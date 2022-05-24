package com.woocommerce.android.analytics

enum class AnalyticsEvent(val siteless: Boolean = false) {
    // -- General
    APPLICATION_OPENED(siteless = true),
    APPLICATION_CLOSED(siteless = true),
    APPLICATION_INSTALLED(siteless = true),
    APPLICATION_UPGRADED(siteless = true),
    APPLICATION_VERSION_CHECK_FAILED(siteless = true),
    BACK_PRESSED(siteless = true),
    VIEW_SHOWN(siteless = true),

    // -- Login
    SIGNED_IN(siteless = true),
    ACCOUNT_LOGOUT(siteless = true),
    LOGIN_ACCESSED(siteless = true),
    LOGIN_MAGIC_LINK_EXITED(siteless = true),
    LOGIN_MAGIC_LINK_FAILED(siteless = true),
    LOGIN_MAGIC_LINK_OPENED(siteless = true),
    LOGIN_MAGIC_LINK_REQUESTED(siteless = true),
    LOGIN_MAGIC_LINK_SUCCEEDED(siteless = true),
    LOGIN_FAILED(siteless = true),
    LOGIN_INSERTED_INVALID_URL(siteless = true),
    LOGIN_AUTOFILL_CREDENTIALS_FILLED(siteless = true),
    LOGIN_AUTOFILL_CREDENTIALS_UPDATED(siteless = true),
    LOGIN_EMAIL_FORM_VIEWED(siteless = true),
    LOGIN_BY_EMAIL_HELP_FINDING_CONNECTED_EMAIL_LINK_TAPPED(siteless = true),
    LOGIN_MAGIC_LINK_OPEN_EMAIL_CLIENT_VIEWED(siteless = true),
    LOGIN_MAGIC_LINK_OPEN_EMAIL_CLIENT_CLICKED(siteless = true),
    LOGIN_MAGIC_LINK_REQUEST_FORM_VIEWED(siteless = true),
    LOGIN_PASSWORD_FORM_VIEWED(siteless = true),
    LOGIN_URL_FORM_VIEWED(siteless = true),
    LOGIN_URL_HELP_SCREEN_VIEWED(siteless = true),
    LOGIN_USERNAME_PASSWORD_FORM_VIEWED(siteless = true),
    LOGIN_TWO_FACTOR_FORM_VIEWED(siteless = true),
    LOGIN_FORGOT_PASSWORD_CLICKED(siteless = true),
    LOGIN_SOCIAL_BUTTON_CLICK(siteless = true),
    LOGIN_SOCIAL_BUTTON_FAILURE(siteless = true),
    LOGIN_SOCIAL_CONNECT_SUCCESS(siteless = true),
    LOGIN_SOCIAL_CONNECT_FAILURE(siteless = true),
    LOGIN_SOCIAL_SUCCESS(siteless = true),
    LOGIN_SOCIAL_FAILURE(siteless = true),
    LOGIN_SOCIAL_2FA_NEEDED(siteless = true),
    LOGIN_SOCIAL_ACCOUNTS_NEED_CONNECTING(siteless = true),
    LOGIN_SOCIAL_ERROR_UNKNOWN_USER(siteless = true),
    LOGIN_WPCOM_BACKGROUND_SERVICE_UPDATE(siteless = true),
    SIGNUP_EMAIL_BUTTON_TAPPED(siteless = true),
    SIGNUP_GOOGLE_BUTTON_TAPPED(siteless = true),
    SIGNUP_TERMS_OF_SERVICE_TAPPED(siteless = true),
    SIGNUP_CANCELED(siteless = true),
    SIGNUP_EMAIL_TO_LOGIN(siteless = true),
    SIGNUP_MAGIC_LINK_FAILED(siteless = true),
    SIGNUP_MAGIC_LINK_OPENED(siteless = true),
    SIGNUP_MAGIC_LINK_OPEN_EMAIL_CLIENT_CLICKED(siteless = true),
    SIGNUP_MAGIC_LINK_SENT(siteless = true),
    SIGNUP_MAGIC_LINK_SUCCEEDED(siteless = true),
    SIGNUP_SOCIAL_ACCOUNTS_NEED_CONNECTING(siteless = true),
    SIGNUP_SOCIAL_BUTTON_FAILURE(siteless = true),
    SIGNUP_SOCIAL_TO_LOGIN(siteless = true),
    ADDED_SELF_HOSTED_SITE(siteless = true),
    CREATED_ACCOUNT(siteless = true),
    LOGIN_PROLOGUE_JETPACK_LOGIN_BUTTON_TAPPED(siteless = true),
    LOGIN_PROLOGUE_JETPACK_CONFIGURATION_INSTRUCTIONS_LINK_TAPPED(siteless = true),
    LOGIN_JETPACK_REQUIRED_SCREEN_VIEWED(siteless = true),
    LOGIN_JETPACK_REQUIRED_VIEW_INSTRUCTIONS_BUTTON_TAPPED(siteless = true),
    LOGIN_JETPACK_REQUIRED_WHAT_IS_JETPACK_LINK_TAPPED(siteless = true),
    LOGIN_JETPACK_REQUIRED_MENU_HELP_TAPPED(siteless = true),
    LOGIN_JETPACK_REQUIRED_SIGN_IN_LINK_TAPPED(siteless = true),
    LOGIN_WHAT_IS_JETPACK_HELP_SCREEN_VIEWED(siteless = true),
    LOGIN_WHAT_IS_JETPACK_HELP_SCREEN_LEARN_MORE_BUTTON_TAPPED(siteless = true),
    LOGIN_WHAT_IS_JETPACK_HELP_SCREEN_OK_BUTTON_TAPPED(siteless = true),
    LOGIN_CONNECTED_SITE_INFO_REQUESTED(siteless = true),
    LOGIN_CONNECTED_SITE_INFO_FAILED(siteless = true),
    LOGIN_CONNECTED_SITE_INFO_SUCCEEDED(siteless = true),
    LOGIN_FIND_CONNECTED_EMAIL_HELP_SCREEN_VIEWED(siteless = true),
    LOGIN_FIND_CONNECTED_EMAIL_HELP_SCREEN_NEED_MORE_HELP_LINK_TAPPED(siteless = true),
    LOGIN_FIND_CONNECTED_EMAIL_HELP_SCREEN_OK_BUTTON_TAPPED(siteless = true),
    LOGIN_NO_JETPACK_SCREEN_VIEWED(siteless = true),
    LOGIN_NO_JETPACK_VIEW_INSTRUCTIONS_BUTTON_TAPPED(siteless = true),
    LOGIN_NO_JETPACK_LOGOUT_LINK_TAPPED(siteless = true),
    LOGIN_NO_JETPACK_TRY_AGAIN_TAPPED(siteless = true),
    LOGIN_NO_JETPACK_MENU_HELP_TAPPED(siteless = true),
    LOGIN_NO_JETPACK_WHAT_IS_JETPACK_LINK_TAPPED(siteless = true),
    LOGIN_DISCOVERY_ERROR_SCREEN_VIEWED(siteless = true),
    LOGIN_DISCOVERY_ERROR_TROUBLESHOOT_BUTTON_TAPPED(siteless = true),
    LOGIN_DISCOVERY_ERROR_TRY_AGAIN_TAPPED(siteless = true),
    LOGIN_DISCOVERY_ERROR_SIGN_IN_WORDPRESS_BUTTON_TAPPED(siteless = true),
    LOGIN_DISCOVERY_ERROR_MENU_HELP_TAPPED(siteless = true),
    LOGIN_MAGIC_LINK_INTERCEPT_SCREEN_VIEWED(siteless = true),
    LOGIN_MAGIC_LINK_INTERCEPT_RETRY_TAPPED(siteless = true),
    LOGIN_MAGIC_LINK_UPDATE_TOKEN_FAILED(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_ACCOUNT_FAILED(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_ACCOUNT_SETTINGS_FAILED(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_SITES_FAILED(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_ACCOUNT_SUCCESS(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_ACCOUNT_SETTINGS_SUCCESS(siteless = true),
    LOGIN_MAGIC_LINK_FETCH_SITES_SUCCESS(siteless = true),
    UNIFIED_LOGIN_STEP(siteless = true),
    UNIFIED_LOGIN_FAILURE(siteless = true),
    UNIFIED_LOGIN_INTERACTION(siteless = true),

    // -- Site Picker
    SITE_PICKER_STORES_SHOWN(siteless = true),
    SITE_PICKER_CONTINUE_TAPPED(siteless = true),
    SITE_PICKER_HELP_BUTTON_TAPPED(siteless = true),
    SITE_PICKER_AUTO_LOGIN_SUBMITTED(siteless = true),
    SITE_PICKER_AUTO_LOGIN_ERROR_NOT_CONNECTED_TO_USER(siteless = true),
    SITE_PICKER_AUTO_LOGIN_ERROR_NOT_WOO_STORE(siteless = true),
    SITE_PICKER_AUTO_LOGIN_ERROR_NOT_CONNECTED_JETPACK(siteless = true),
    SITE_PICKER_TRY_ANOTHER_ACCOUNT_BUTTON_TAPPED(siteless = true),
    SITE_PICKER_TRY_ANOTHER_STORE_BUTTON_TAPPED(siteless = true),
    SITE_PICKER_VIEW_CONNECTED_STORES_BUTTON_TAPPED(siteless = true),
    SITE_PICKER_HELP_FINDING_CONNECTED_EMAIL_LINK_TAPPED(siteless = true),
    SITE_PICKER_NOT_WOO_STORE_REFRESH_APP_LINK_TAPPED(siteless = true),
    SITE_PICKER_NOT_CONNECTED_JETPACK_REFRESH_APP_LINK_TAPPED(siteless = true),

    // -- Dashboard
    DASHBOARD_PULLED_TO_REFRESH,
    DASHBOARD_SHARE_YOUR_STORE_BUTTON_TAPPED,
    DASHBOARD_UNFULFILLED_ORDERS_BUTTON_TAPPED,
    DASHBOARD_MAIN_STATS_DATE,
    DASHBOARD_MAIN_STATS_LOADED,
    DASHBOARD_TOP_PERFORMERS_DATE,
    DASHBOARD_TOP_PERFORMERS_LOADED,
    DASHBOARD_NEW_STATS_REVERTED_BANNER_DISMISS_TAPPED,
    DASHBOARD_NEW_STATS_REVERTED_BANNER_LEARN_MORE_TAPPED,
    DASHBOARD_NEW_STATS_AVAILABILITY_BANNER_CANCEL_TAPPED,
    DASHBOARD_NEW_STATS_AVAILABILITY_BANNER_TRY_TAPPED,
    USED_ANALYTICS,

    // -- Orders List
    ORDERS_LIST_FILTER,
    ORDERS_LIST_SEARCH,
    ORDERS_LIST_LOADED,
    ORDERS_LIST_SHARE_YOUR_STORE_BUTTON_TAPPED,
    ORDERS_LIST_PULLED_TO_REFRESH,
    ORDERS_LIST_MENU_SEARCH_TAPPED,
    ORDERS_LIST_VIEW_FILTER_OPTIONS_TAPPED,

    // -- Order filter by status dialog
    FILTER_ORDERS_BY_STATUS_DIALOG_APPLY_FILTER_BUTTON_TAPPED,
    FILTER_ORDERS_BY_STATUS_DIALOG_OPTION_SELECTED,

    // -- Simple Payments
    SIMPLE_PAYMENTS_FLOW_STARTED,
    SIMPLE_PAYMENTS_FLOW_COMPLETED,
    SIMPLE_PAYMENTS_FLOW_COLLECT,
    SIMPLE_PAYMENTS_FLOW_FAILED,
    SIMPLE_PAYMENTS_FLOW_CANCELED,
    SIMPLE_PAYMENTS_FLOW_NOTE_ADDED,
    SIMPLE_PAYMENTS_FLOW_TAXES_TOGGLED,
    SETTINGS_BETA_FEATURES_SIMPLE_PAYMENTS_TOGGLED,

    // -- Order Detail
    ORDER_OPEN,
    ORDER_NOTES_LOADED,
    ORDER_CONTACT_ACTION,
    ORDER_CONTACT_ACTION_FAILED,
    ORDER_STATUS_CHANGE,
    ORDER_STATUS_CHANGE_FAILED,
    ORDER_STATUS_CHANGE_SUCCESS,
    ORDER_STATUS_CHANGE_UNDO,
    ORDER_DETAIL_PULLED_TO_REFRESH,
    ORDER_DETAIL_ADD_NOTE_BUTTON_TAPPED,
    ORDER_DETAIL_CUSTOMER_INFO_SHOW_BILLING_TAPPED,
    ORDER_DETAIL_CUSTOMER_INFO_HIDE_BILLING_TAPPED,
    ORDER_DETAIL_CUSTOMER_INFO_EMAIL_MENU_EMAIL_TAPPED,
    ORDER_DETAIL_CUSTOMER_INFO_PHONE_MENU_PHONE_TAPPED,
    ORDER_DETAIL_CUSTOMER_INFO_PHONE_MENU_SMS_TAPPED,
    ORDER_DETAIL_FULFILL_ORDER_BUTTON_TAPPED,
    ORDER_DETAIL_ORDER_STATUS_EDIT_BUTTON_TAPPED,
    ORDER_DETAIL_PRODUCT_TAPPED,
    ORDER_DETAIL_PRODUCT_DETAIL_BUTTON_TAPPED,
    ORDER_TRACKING_LOADED,
    ORDER_DETAIL_TRACKING_DELETE_BUTTON_TAPPED,
    ORDER_DETAIL_TRACKING_ADD_TRACKING_BUTTON_TAPPED,
    ORDER_DETAIL_ISSUE_REFUND_BUTTON_TAPPED,
    ORDER_DETAIL_VIEW_REFUND_DETAILS_BUTTON_TAPPED,
    ORDER_DETAIL_CREATE_SHIPPING_LABEL_BUTTON_TAPPED,
    ORDER_DETAIL_PAYMENT_LINK_SHARED,

    // - Order detail editing
    ORDER_DETAIL_EDIT_FLOW_STARTED,
    ORDER_DETAIL_EDIT_FLOW_COMPLETED,
    ORDER_DETAIL_EDIT_FLOW_FAILED,
    ORDER_DETAIL_EDIT_FLOW_CANCELED,

    // -- Order Creation
    ORDER_ADD_NEW,
    ORDER_PRODUCT_ADD,
    ORDER_CUSTOMER_ADD,
    ORDER_FEE_ADD,
    ORDER_SHIPPING_METHOD_ADD,
    ORDER_CREATE_BUTTON_TAPPED,
    ORDER_CREATION_SUCCESS,
    ORDER_CREATION_FAILED,
    ORDER_SYNC_FAILED,

    // -- Refunds
    CREATE_ORDER_REFUND_NEXT_BUTTON_TAPPED,
    CREATE_ORDER_REFUND_TAB_CHANGED,
    CREATE_ORDER_REFUND_SELECT_ALL_ITEMS_BUTTON_TAPPED,
    CREATE_ORDER_REFUND_ITEM_QUANTITY_DIALOG_OPENED,
    CREATE_ORDER_REFUND_PRODUCT_AMOUNT_DIALOG_OPENED,
    CREATE_ORDER_REFUND_SUMMARY_REFUND_BUTTON_TAPPED,
    CREATE_ORDER_REFUND_SUMMARY_UNDO_BUTTON_TAPPED,
    REFUND_CREATE,
    REFUND_CREATE_SUCCESS,
    REFUND_CREATE_FAILED,

    // -- Order Notes
    ADD_ORDER_NOTE_ADD_BUTTON_TAPPED,
    ADD_ORDER_NOTE_EMAIL_NOTE_TO_CUSTOMER_TOGGLED,
    ORDER_NOTE_ADD,
    ORDER_NOTE_ADD_FAILED,
    ORDER_NOTE_ADD_SUCCESS,

    // -- Order Shipment Tracking
    ORDER_SHIPMENT_TRACKING_CARRIER_SELECTED,
    ORDER_TRACKING_ADD,
    ORDER_TRACKING_ADD_FAILED,
    ORDER_TRACKING_ADD_SUCCESS,
    ORDER_SHIPMENT_TRACKING_ADD_BUTTON_TAPPED,
    ORDER_SHIPMENT_TRACKING_CUSTOM_PROVIDER_SELECTED,
    ORDER_TRACKING_DELETE_SUCCESS,
    ORDER_TRACKING_DELETE_FAILED,
    ORDER_TRACKING_PROVIDERS_LOADED,
    SHIPMENT_TRACKING_MENU_ACTION,

    // -- Shipping Labels
    SHIPPING_LABEL_API_REQUEST,
    SHIPPING_LABEL_PRINT_REQUESTED,
    SHIPPING_LABEL_REFUND_REQUESTED,
    SHIPPING_LABEL_PURCHASE_FLOW,
    SHIPPING_LABEL_DISCOUNT_INFO_BUTTON_TAPPED,
    SHIPPING_LABEL_EDIT_ADDRESS_DONE_BUTTON_TAPPED,
    SHIPPING_LABEL_EDIT_ADDRESS_USE_ADDRESS_AS_IS_BUTTON_TAPPED,
    SHIPPING_LABEL_EDIT_ADDRESS_OPEN_MAP_BUTTON_TAPPED,
    SHIPPING_LABEL_EDIT_ADDRESS_CONTACT_CUSTOMER_BUTTON_TAPPED,
    SHIPPING_LABEL_ADDRESS_SUGGESTIONS_USE_SELECTED_ADDRESS_BUTTON_TAPPED,
    SHIPPING_LABEL_ADDRESS_SUGGESTIONS_EDIT_SELECTED_ADDRESS_BUTTON_TAPPED,
    SHIPPING_LABEL_ADDRESS_VALIDATION_FAILED,
    SHIPPING_LABEL_ADDRESS_VALIDATION_SUCCEEDED,
    SHIPPING_LABEL_ORDER_FULFILL_SUCCEEDED,
    SHIPPING_LABEL_ORDER_FULFILL_FAILED,
    SHIPPING_LABEL_MOVE_ITEM_TAPPED,
    SHIPPING_LABEL_ITEM_MOVED,
    SHIPPING_LABEL_ADD_PAYMENT_METHOD_TAPPED,
    SHIPPING_LABEL_PAYMENT_METHOD_ADDED,
    SHIPPING_LABEL_ADD_PACKAGE_TAPPED,
    SHIPPING_LABEL_PACKAGE_ADDED_SUCCESSFULLY,
    SHIPPING_LABEL_ADD_PACKAGE_FAILED,
    SHIPPING_LABEL_ORDER_IS_ELIGIBLE,

    // -- Card Present Payments - onboarding
    CARD_PRESENT_ONBOARDING_LEARN_MORE_TAPPED,
    CARD_PRESENT_ONBOARDING_NOT_COMPLETED,

    // -- Card Present Payments - collection
    CARD_PRESENT_COLLECT_PAYMENT_TAPPED,
    CARD_PRESENT_COLLECT_PAYMENT_FAILED,
    CARD_PRESENT_COLLECT_PAYMENT_CANCELLED,
    CARD_PRESENT_COLLECT_PAYMENT_SUCCESS,

    // --Card Present Payments - Interac refund
    CARD_PRESENT_COLLECT_INTERAC_PAYMENT_SUCCESS,
    CARD_PRESENT_COLLECT_INTERAC_PAYMENT_FAILED,
    CARD_PRESENT_COLLECT_INTERAC_REFUND_CANCELLED,

    // -- Card Reader - discovery
    CARD_READER_DISCOVERY_TAPPED,
    CARD_READER_DISCOVERY_FAILED,
    CARD_READER_DISCOVERY_READER_DISCOVERED,

    // -- Card Reader - connection
    CARD_READER_CONNECTION_TAPPED,
    CARD_READER_CONNECTION_FAILED,
    CARD_READER_CONNECTION_SUCCESS,
    CARD_READER_DISCONNECT_TAPPED,
    CARD_READER_AUTO_CONNECTION_STARTED,

    // -- Card Reader - software update
    CARD_READER_SOFTWARE_UPDATE_STARTED,
    CARD_READER_SOFTWARE_UPDATE_SUCCESS,
    CARD_READER_SOFTWARE_UPDATE_FAILED,

    // -- Card Reader - Location
    CARD_READER_LOCATION_SUCCESS,
    CARD_READER_LOCATION_FAILURE,
    CARD_READER_LOCATION_MISSING_TAPPED,

    // -- Receipts
    RECEIPT_PRINT_TAPPED,
    RECEIPT_EMAIL_TAPPED,
    RECEIPT_EMAIL_FAILED,
    RECEIPT_PRINT_FAILED,
    RECEIPT_PRINT_CANCELED,
    RECEIPT_PRINT_SUCCESS,

    // -- Top-level navigation
    MAIN_MENU_SETTINGS_TAPPED,
    MAIN_MENU_CONTACT_SUPPORT_TAPPED,
    MAIN_TAB_DASHBOARD_SELECTED,
    MAIN_TAB_DASHBOARD_RESELECTED,
    MAIN_TAB_ANALYTICS_SELECTED,
    MAIN_TAB_ANALYTICS_RESELECTED,
    MAIN_TAB_ORDERS_SELECTED,
    MAIN_TAB_ORDERS_RESELECTED,
    MAIN_TAB_PRODUCTS_SELECTED,
    MAIN_TAB_PRODUCTS_RESELECTED,
    MAIN_TAB_HUB_MENU_SELECTED,
    MAIN_TAB_HUB_MENU_RESELECTED,

    // -- Settings
    SETTING_CHANGE,
    SETTING_CHANGE_FAILED,
    SETTING_CHANGE_SUCCESS,
    SETTINGS_SELECTED_SITE_TAPPED,
    SETTINGS_LOGOUT_BUTTON_TAPPED,
    SETTINGS_LOGOUT_CONFIRMATION_DIALOG_RESULT,
    SETTINGS_BETA_FEATURES_BUTTON_TAPPED,
    SETTINGS_PRIVACY_SETTINGS_BUTTON_TAPPED,
    SETTINGS_FEATURE_REQUEST_BUTTON_TAPPED,
    SETTINGS_ABOUT_WOOCOMMERCE_LINK_TAPPED,
    SETTINGS_ABOUT_BUTTON_TAPPED,
    SETTINGS_ABOUT_OPEN_SOURCE_LICENSES_LINK_TAPPED,
    SETTINGS_NOTIFICATIONS_OPEN_CHANNEL_SETTINGS_BUTTON_TAPPED,
    SETTINGS_WE_ARE_HIRING_BUTTON_TAPPED,
    SETTINGS_BETA_FEATURES_PRODUCTS_TOGGLED,
    SETTINGS_IMAGE_OPTIMIZATION_TOGGLED,
    PRIVACY_SETTINGS_COLLECT_INFO_TOGGLED,
    PRIVACY_SETTINGS_PRIVACY_POLICY_LINK_TAPPED,
    PRIVACY_SETTINGS_SHARE_INFO_LINK_TAPPED,
    PRIVACY_SETTINGS_THIRD_PARTY_TRACKING_INFO_LINK_TAPPED,
    PRIVACY_SETTINGS_CRASH_REPORTING_TOGGLED,

    // -- Product list
    PRODUCT_LIST_LOADED,
    PRODUCT_LIST_LOAD_ERROR,
    PRODUCT_LIST_PRODUCT_TAPPED,
    PRODUCT_LIST_PULLED_TO_REFRESH,
    PRODUCT_LIST_SEARCHED,
    PRODUCT_LIST_MENU_SEARCH_TAPPED,
    PRODUCT_LIST_VIEW_FILTER_OPTIONS_TAPPED,
    PRODUCT_LIST_VIEW_SORTING_OPTIONS_TAPPED,
    PRODUCT_LIST_SORTING_OPTION_SELECTED,
    PRODUCT_LIST_ADD_PRODUCT_BUTTON_TAPPED,
    ADD_PRODUCT_PRODUCT_TYPE_SELECTED,

    // -- Product detail
    PRODUCT_DETAIL_LOADED,
    PRODUCT_DETAIL_IMAGE_TAPPED,
    PRODUCT_DETAIL_SHARE_BUTTON_TAPPED,
    PRODUCT_DETAIL_UPDATE_BUTTON_TAPPED,
    PRODUCT_DETAIL_VIEW_EXTERNAL_TAPPED,
    PRODUCT_DETAIL_VIEW_AFFILIATE_TAPPED,
    PRODUCT_DETAIL_VIEW_PRODUCT_VARIANTS_TAPPED,
    PRODUCT_DETAIL_VIEW_PRODUCT_DESCRIPTION_TAPPED,
    PRODUCT_DETAIL_VIEW_PRICE_SETTINGS_TAPPED,
    PRODUCT_DETAIL_VIEW_INVENTORY_SETTINGS_TAPPED,
    PRODUCT_DETAIL_VIEW_SHIPPING_SETTINGS_TAPPED,
    PRODUCT_DETAIL_VIEW_SHORT_DESCRIPTION_TAPPED,
    PRODUCT_DETAIL_VIEW_CATEGORIES_TAPPED,
    PRODUCT_DETAIL_VIEW_TAGS_TAPPED,
    PRODUCT_DETAIL_VIEW_PRODUCT_TYPE_TAPPED,
    PRODUCT_DETAIL_VIEW_PRODUCT_REVIEWS_TAPPED,
    PRODUCT_DETAIL_VIEW_GROUPED_PRODUCTS_TAPPED,
    PRODUCT_DETAIL_VIEW_LINKED_PRODUCTS_TAPPED,
    PRODUCT_DETAIL_VIEW_DOWNLOADABLE_FILES_TAPPED,
    PRODUCT_PRICE_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_INVENTORY_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_SHIPPING_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_IMAGE_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_CATEGORY_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_TAG_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_DETAIL_UPDATE_SUCCESS,
    PRODUCT_DETAIL_UPDATE_ERROR,
    ADD_PRODUCT_PUBLISH_TAPPED,
    ADD_PRODUCT_SAVE_AS_DRAFT_TAPPED,
    ADD_PRODUCT_SUCCESS,
    ADD_PRODUCT_FAILED,
    PRODUCT_IMAGE_UPLOAD_FAILED,
    PRODUCT_DETAIL_PRODUCT_DELETED,

    // -- Product Categories
    PRODUCT_CATEGORIES_LOADED,
    PRODUCT_CATEGORIES_LOAD_FAILED,
    PRODUCT_CATEGORIES_PULLED_TO_REFRESH,
    PRODUCT_CATEGORY_SETTINGS_ADD_BUTTON_TAPPED,

    // -- Add Product Category
    PARENT_CATEGORIES_LOADED,
    PARENT_CATEGORIES_LOAD_FAILED,
    PARENT_CATEGORIES_PULLED_TO_REFRESH,
    ADD_PRODUCT_CATEGORY_SAVE_TAPPED,

    // -- Product Tags
    PRODUCT_TAGS_LOADED,
    PRODUCT_TAGS_LOAD_FAILED,
    PRODUCT_TAGS_PULLED_TO_REFRESH,

    // -- Product reviews
    PRODUCT_REVIEWS_LOADED,
    PRODUCT_REVIEWS_LOAD_FAILED,
    PRODUCT_REVIEWS_PULLED_TO_REFRESH,

    // -- Downloadable Files
    PRODUCT_DOWNLOADABLE_FILES_SETTINGS_CHANGED,
    PRODUCTS_DOWNLOADABLE_FILE,

    // -- Linked Products
    LINKED_PRODUCTS,

    // -- Connected Products (Grouped products, Upsells, Cross-sells)
    CONNECTED_PRODUCTS_LIST,

    // -- Product external link
    PRODUCT_DETAIL_VIEW_EXTERNAL_PRODUCT_LINK_TAPPED,
    EXTERNAL_PRODUCT_LINK_SETTINGS_DONE_BUTTON_TAPPED,

    // -- Product attributes
    PRODUCT_ATTRIBUTE_EDIT_BUTTON_TAPPED,
    PRODUCT_ATTRIBUTE_ADD_BUTTON_TAPPED,
    PRODUCT_ATTRIBUTE_UPDATED,
    PRODUCT_ATTRIBUTE_UPDATE_SUCCESS,
    PRODUCT_ATTRIBUTE_UPDATE_FAILED,
    PRODUCT_ATTRIBUTE_RENAME_BUTTON_TAPPED,
    PRODUCT_ATTRIBUTE_REMOVE_BUTTON_TAPPED,
    PRODUCT_ATTRIBUTE_OPTIONS_ROW_TAPPED,

    // -- Product variation
    PRODUCT_VARIATION_VIEW_VARIATION_DESCRIPTION_TAPPED,
    PRODUCT_VARIATION_VIEW_PRICE_SETTINGS_TAPPED,
    PRODUCT_VARIATION_VIEW_INVENTORY_SETTINGS_TAPPED,
    PRODUCT_VARIATION_VIEW_SHIPPING_SETTINGS_TAPPED,
    PRODUCT_VARIATION_VIEW_VARIATION_DETAIL_TAPPED,
    PRODUCT_VARIATION_VIEW_VARIATION_VISIBILITY_SWITCH_TAPPED,
    PRODUCT_VARIATION_IMAGE_TAPPED,
    PRODUCT_VARIATION_UPDATE_BUTTON_TAPPED,
    PRODUCT_VARIATION_UPDATE_SUCCESS,
    PRODUCT_VARIATION_UPDATE_ERROR,
    PRODUCT_VARIATION_LOADED,
    PRODUCT_VARIATION_ADD_FIRST_TAPPED,
    PRODUCT_VARIATION_ADD_MORE_TAPPED,
    PRODUCT_VARIATION_CREATION_SUCCESS,
    PRODUCT_VARIATION_CREATION_FAILED,
    PRODUCT_VARIATION_REMOVE_BUTTON_TAPPED,
    PRODUCT_VARIATION_EDIT_ATTRIBUTE_DONE_BUTTON_TAPPED,
    PRODUCT_VARIATION_EDIT_ATTRIBUTE_OPTIONS_DONE_BUTTON_TAPPED,
    PRODUCT_VARIATION_ATTRIBUTE_ADDED_BACK_BUTTON_TAPPED,

    // -- Product Add-ons
    PRODUCT_ADDONS_BETA_FEATURES_SWITCH_TOGGLED,
    PRODUCT_ADDONS_ORDER_ADDONS_VIEWED,
    PRODUCT_ADDONS_PRODUCT_DETAIL_VIEW_PRODUCT_ADDONS_TAPPED,
    PRODUCT_ADDONS_ORDER_DETAIL_VIEW_PRODUCT_ADDONS_TAPPED,
    PRODUCT_ADDONS_REFUND_DETAIL_VIEW_PRODUCT_ADDONS_TAPPED,

    // -- Product settings
    PRODUCT_SETTINGS_DONE_BUTTON_TAPPED,
    PRODUCT_DETAIL_ADD_IMAGE_TAPPED,
    PRODUCT_IMAGE_SETTINGS_ADD_IMAGES_BUTTON_TAPPED,
    PRODUCT_IMAGE_SETTINGS_ADD_IMAGES_SOURCE_TAPPED,
    PRODUCT_IMAGE_SETTINGS_DELETE_IMAGE_BUTTON_TAPPED,
    PRODUCT_SETTINGS_STATUS_TAPPED,
    PRODUCT_SETTINGS_CATALOG_VISIBILITY_TAPPED,
    PRODUCT_SETTINGS_SLUG_TAPPED,
    PRODUCT_SETTINGS_PURCHASE_NOTE_TAPPED,
    PRODUCT_SETTINGS_VISIBILITY_TAPPED,
    PRODUCT_SETTINGS_MENU_ORDER_TAPPED,
    PRODUCT_SETTINGS_VIRTUAL_TOGGLED,
    PRODUCT_SETTINGS_REVIEWS_TOGGLED,

    // -- Product filters
    PRODUCT_FILTER_LIST_SHOW_PRODUCTS_BUTTON_TAPPED,
    PRODUCT_FILTER_LIST_CLEAR_MENU_BUTTON_TAPPED,

    // -- Aztec editor
    AZTEC_EDITOR_DONE_BUTTON_TAPPED,

    // -- Product variations
    PRODUCT_VARIANTS_PULLED_TO_REFRESH,
    PRODUCT_VARIANTS_LOADED,
    PRODUCT_VARIANTS_LOAD_ERROR,
    PRODUCT_VARIANTS_BULK_UPDATE_TAPPED,
    PRODUCT_VARIANTS_BULK_UPDATE_REGULAR_PRICE_TAPPED,
    PRODUCT_VARIANTS_BULK_UPDATE_SALE_PRICE_TAPPED,

    // -- Product images
    PRODUCT_IMAGE_ADDED,
    PRODUCT_IMAGE_REMOVED,

    // -- Help & Support
    SUPPORT_HELP_CENTER_VIEWED(siteless = true),
    SUPPORT_IDENTITY_SET(siteless = true),
    SUPPORT_IDENTITY_FORM_VIEWED(siteless = true),
    SUPPORT_APPLICATION_LOG_VIEWED(siteless = true),
    SUPPORT_TICKETS_VIEWED(siteless = true),
    SUPPORT_FAQ_VIEWED(siteless = true),
    SUPPORT_SSR_OPENED,
    SUPPORT_SSR_COPY_BUTTON_TAPPED,

    // -- Push notifications
    PUSH_NOTIFICATION_RECEIVED,
    PUSH_NOTIFICATION_TAPPED,

    // -- Notifications List
    NOTIFICATION_OPEN,
    NOTIFICATIONS_LOADED,
    NOTIFICATIONS_LOAD_FAILED,

    // -- Product Review List
    REVIEWS_LOADED,
    REVIEWS_LOAD_FAILED,
    REVIEWS_PRODUCTS_LOADED,
    REVIEWS_PRODUCTS_LOAD_FAILED,
    REVIEWS_MARK_ALL_READ,
    REVIEWS_MARK_ALL_READ_SUCCESS,
    REVIEWS_MARK_ALL_READ_FAILED,
    REVIEWS_LIST_PULLED_TO_REFRESH,
    REVIEWS_LIST_MENU_MARK_READ_BUTTON_TAPPED,
    REVIEWS_LIST_SHARE_YOUR_STORE_BUTTON_TAPPED,

    // -- Product Review Detail
    REVIEW_LOADED,
    REVIEW_LOAD_FAILED,
    REVIEW_PRODUCT_LOADED,
    REVIEW_PRODUCT_LOAD_FAILED,
    REVIEW_MARK_READ,
    REVIEW_MARK_READ_SUCCESS,
    REVIEW_MARK_READ_FAILED,
    REVIEW_ACTION,
    REVIEW_ACTION_FAILED,
    REVIEW_ACTION_SUCCESS,
    REVIEW_ACTION_UNDO,
    SNACK_REVIEW_ACTION_APPLIED_UNDO_BUTTON_TAPPED,
    REVIEW_DETAIL_APPROVE_BUTTON_TAPPED,
    REVIEW_DETAIL_OPEN_EXTERNAL_BUTTON_TAPPED,
    REVIEW_DETAIL_SPAM_BUTTON_TAPPED,
    REVIEW_DETAIL_TRASH_BUTTON_TAPPED,

    // -- In-App Feedback
    APP_FEEDBACK_PROMPT,
    APP_FEEDBACK_RATE_APP,
    SURVEY_SCREEN,
    FEATURE_FEEDBACK_BANNER,

    // -- Errors
    JETPACK_TUNNEL_TIMEOUT,

    // -- Order status changes
    SET_ORDER_STATUS_DIALOG_APPLY_BUTTON_TAPPED,

    // -- Application permissions
    APP_PERMISSION_GRANTED,
    APP_PERMISSION_DENIED,

    // -- Encrypted logging
    ENCRYPTED_LOGGING_UPLOAD_SUCCESSFUL,
    ENCRYPTED_LOGGING_UPLOAD_FAILED,

    // -- What's new / feature announcements
    FEATURE_ANNOUNCEMENT_SHOWN,

    // -- Jetpack CP
    JETPACK_CP_SITES_FETCHED,
    FEATURE_JETPACK_BENEFITS_BANNER,
    JETPACK_INSTALL_BUTTON_TAPPED,
    JETPACK_INSTALL_SUCCEEDED,
    JETPACK_INSTALL_FAILED,
    JETPACK_INSTALL_IN_WPADMIN_BUTTON_TAPPED,
    JETPACK_INSTALL_CONTACT_SUPPORT_BUTTON_TAPPED,

    // -- Other
    UNFULFILLED_ORDERS_LOADED,
    TOP_EARNER_PRODUCT_TAPPED,

    // -- Media picker
    MEDIA_PICKER_PREVIEW_OPENED,
    MEDIA_PICKER_RECENT_MEDIA_SELECTED,
    MEDIA_PICKER_OPEN_GIF_LIBRARY,
    MEDIA_PICKER_OPEN_DEVICE_LIBRARY,
    MEDIA_PICKER_CAPTURE_PHOTO,
    MEDIA_PICKER_SEARCH_TRIGGERED,
    MEDIA_PICKER_SEARCH_EXPANDED,
    MEDIA_PICKER_SEARCH_COLLAPSED,
    MEDIA_PICKER_SHOW_PERMISSIONS_SCREEN,
    MEDIA_PICKER_ITEM_SELECTED,
    MEDIA_PICKER_ITEM_UNSELECTED,
    MEDIA_PICKER_SELECTION_CLEARED,
    MEDIA_PICKER_OPENED,
    MEDIA_PICKER_OPEN_SYSTEM_PICKER,
    MEDIA_PICKER_OPEN_WORDPRESS_MEDIA_LIBRARY_PICKER,

    // -- More Menu (aka Hub Menu)
    HUB_MENU_SWITCH_STORE_TAPPED,
    HUB_MENU_OPTION_TAPPED,
    HUB_MENU_SETTINGS_TAPPED,

    // Inbox
    INBOX_NOTES_LOADED,
    INBOX_NOTES_LOAD_FAILED,
    INBOX_NOTE_ACTION,

    // Coupons
    COUPONS_LOADED,
    COUPONS_LOAD_FAILED,
    COUPONS_LIST_SEARCH_TAPPED,
    COUPON_DETAILS,
}
