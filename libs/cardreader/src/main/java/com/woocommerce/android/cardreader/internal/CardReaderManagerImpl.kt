package com.woocommerce.android.cardreader.internal

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import com.stripe.stripeterminal.log.LogLevel
import com.woocommerce.android.cardreader.BuildConfig
import com.woocommerce.android.cardreader.CardPaymentStatus
import com.woocommerce.android.cardreader.CardReaderManager
import com.woocommerce.android.cardreader.PaymentData
import com.woocommerce.android.cardreader.connection.CardReader
import com.woocommerce.android.cardreader.connection.CardReaderDiscoveryEvents
import com.woocommerce.android.cardreader.internal.connection.ConnectionManager
import com.woocommerce.android.cardreader.internal.connection.TerminalListenerImpl
import com.woocommerce.android.cardreader.internal.firmware.SoftwareUpdateManager
import com.woocommerce.android.cardreader.internal.payments.PaymentManager
import com.woocommerce.android.cardreader.internal.wrappers.LogWrapper
import com.woocommerce.android.cardreader.internal.wrappers.TerminalWrapper
import com.woocommerce.android.cardreader.payments.PaymentInfo
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of CardReaderManager using StripeTerminalSDK.
 */
@Suppress("LongParameterList")
internal class CardReaderManagerImpl(
    private val terminal: TerminalWrapper,
    private val tokenProvider: TokenProvider,
    private val logWrapper: LogWrapper,
    private val paymentManager: PaymentManager,
    private val connectionManager: ConnectionManager,
    private val softwareUpdateManager: SoftwareUpdateManager,
    private val terminalListener: TerminalListenerImpl,
) : CardReaderManager {
    companion object {
        private const val TAG = "CardReaderManager"
    }

    private lateinit var application: Application

    override val isInitialized: Boolean
        get() {
            return terminal.isInitialized()
        }

    override val readerStatus = terminalListener.readerStatus

    override val softwareUpdateStatus = connectionManager.softwareUpdateStatus

    override val softwareUpdateAvailability = connectionManager.softwareUpdateAvailability

    override fun resetSoftwareUpdateStatus() {
        connectionManager.resetSoftwareUpdateStatus()
    }

    override fun initialize(app: Application) {
        if (!terminal.isInitialized()) {
            application = app

            app.registerComponentCallbacks(object : ComponentCallbacks2 {
                override fun onConfigurationChanged(newConfig: Configuration) {}

                override fun onLowMemory() {}

                override fun onTrimMemory(level: Int) {
                    terminal.getLifecycleObserver().onTrimMemory(application, level)
                }
            })

            val logLevel = if (BuildConfig.DEBUG) LogLevel.VERBOSE else LogLevel.ERROR

            initStripeTerminal(logLevel)

            terminal.setupSimulator()
        } else {
            logWrapper.w(TAG, "CardReaderManager is already initialized")
        }
    }

    override fun discoverReaders(isSimulated: Boolean): Flow<CardReaderDiscoveryEvents> {
        if (!terminal.isInitialized()) throw IllegalStateException("Terminal not initialized")
        return connectionManager.discoverReaders(isSimulated)
    }

    override suspend fun connectToReader(cardReader: CardReader): Boolean {
        if (!terminal.isInitialized()) throw IllegalStateException("Terminal not initialized")
        return connectionManager.connectToReader(cardReader)
    }

    override suspend fun disconnectReader(): Boolean {
        if (!terminal.isInitialized()) throw IllegalStateException("Terminal not initialized")
        if (terminal.getConnectedReader() == null) return false
        return connectionManager.disconnectReader()
    }

    override suspend fun collectPayment(paymentInfo: PaymentInfo): Flow<CardPaymentStatus> =
        paymentManager.acceptPayment(paymentInfo)

    override suspend fun retryCollectPayment(orderId: Long, paymentData: PaymentData): Flow<CardPaymentStatus> =
        paymentManager.retryPayment(orderId, paymentData)

    override fun cancelPayment(paymentData: PaymentData) = paymentManager.cancelPayment(paymentData)

    private fun initStripeTerminal(logLevel: LogLevel) {
        terminal.initTerminal(application, logLevel, tokenProvider, terminalListener)
    }

    override suspend fun installSoftwareUpdate() {
        if (!terminal.isInitialized()) throw IllegalStateException("Terminal not initialized")
        softwareUpdateManager.updateSoftware()
    }

    override suspend fun clearCachedCredentials() {
        if (!terminal.isInitialized()) throw IllegalStateException("Terminal not initialized")
        terminal.clearCachedCredentials()
    }
}
