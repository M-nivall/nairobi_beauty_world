package com.example.Varsani.utils;

public class Urls {

    public static String ipAddress = "https://e0ba16f98ac3.ngrok-free.app/nairobibeauty/";
    //public static String ipAddress = "http://192.168.100.240/nairobibeauty/";

    private static final String ROOT_URL =ipAddress+ "android_files/";
    public static final String ROOT_URL_IMAGES =ipAddress+"upload_products/" ;

    public static  final String URL_PRINT=ipAddress+"print_pdf.php";
    public static  final String UEL_FEEDBACK=ROOT_URL+"client/get_feedback.php";
    public static  final String UEL_FEEDBACK_SEND=ROOT_URL+"client/send_feedback.php";

    public static  final String UEL_STAFF_SEND_FEEDBACK=ROOT_URL+"client/staff_sendfeedback.php";
    public static  final String UEL_STAFF_FEEDBACK=ROOT_URL+"client/getstafffeedback.php";

     //  products
    public static final String URL_GET_PRODUCTS=ROOT_URL + "client/products.php";
    public static final String URL_ADD_CART=ROOT_URL + "client/add_to_cart.php";
    public static final String URL_GET_CART=ROOT_URL + "client/cart.php";
    public static final String URL_UPDATE_CART=ROOT_URL + "client/car_update.php";
    public static final String URL_REMOVE_ITEM=ROOT_URL + "client/cart_remove.php";

    //services
    public static final String URL_GET_SERVICES=ROOT_URL + "client/services.php";
    public static final String URL_ADD_CART2=ROOT_URL + "client/add_to_cart2.php";
    public static final String URL_GET_CART2=ROOT_URL + "client/cart2.php";
    public static final String URL_REMOVE_BOOKING=ROOT_URL + "client/booking_remove.php";
    public static final String URL_SUBMIT_REQUEST = ROOT_URL+"client/submit_request.php";

    // shipping
    public static final String URL_GET_COUNTIES=ROOT_URL + "client/counties.php";
    public static final String URL_GET_TOWNS=ROOT_URL + "client/towns.php";
    public static final String URL_DELIVERY_DETAILS=ROOT_URL + "client/delivery_details.php";

    // checkout
    public static final String URL_GET_CHECKOUT_TOTAL=ROOT_URL + "client/checkout_cost.php";
    // user
    public static final String URL_REG = ROOT_URL +"client/register.php";
    public static final String URL_LOGIN= ROOT_URL+"client/login.php";
    public static final String URL_RESET = ROOT_URL + "client/forgotpass.php";
    public static final String URL_RESET2 = ROOT_URL + "client/resetpass.php";
//    SUPPLIERS
    public static final String URL_REG_SUPPLIERS= ROOT_URL+"supplier/reg_supplier.php";
    public static final String URL_MY_REQUESTS= ROOT_URL+"supplier/my_requests.php";
    public static final String URL_ACCEPT= ROOT_URL+"supplier/approve_items.php";
   // orders
    public static final String URL_SUBMIT_ORDER = ROOT_URL+"client/submit_order.php";
    public static final String URL_GET_ORDERS= ROOT_URL+"client/order_history.php";
    public static final String URL_GET_ORDER_ITEMS= ROOT_URL+"client/order_items.php";
    public static final String URL_GET_ORDER_ITEMS2= ROOT_URL+"client/order_items2.php";
    public static final String URL_MARK_DELIVERED= ROOT_URL+"client/mark_delivered.php";
    public static final String URL_MARK_REJECTED= ROOT_URL+"client/mark_rejected.php";
    public static final String URL_MARK_COMPLETE= ROOT_URL+"client/mark_completed.php";

    //invoices
    public static final String URL_GET_INVOICE= ROOT_URL+"client/invoice_history.php";
    public static final String URL_SUBMIT_INVOICE= ROOT_URL+"client/submit_invoice.php";

    //Staff
    public static final String URL_STAFF_LOGIN=ROOT_URL + "staff_login.php";
    //Finance
    public static final String URL_NEW_ORDERS=ROOT_URL + "finance/new_orders.php";
    public static final String URL_GET_CLIENT_ITEMS=ROOT_URL + "client_item.php";
    public static final String URL_GET_APPROVE_ORDERS=ROOT_URL + "finance/approve_order.php";
    public static final String URL_APPROVED_ORDERS=ROOT_URL + "finance/approved_orders.php";
    public static final String URL_NEW_SERV_PAYMENTS=ROOT_URL + "finance/new_serv_payments.php";
    public static final String URL_APPROVE_SERV_PAYMENTS=ROOT_URL + "finance/approve_serv_payments.php";
    public static final String URL_APPROVED_SERV_PAYMENTS=ROOT_URL + "finance/approved_serv_payments.php";
    public static final String URL_SUPPLY_PAYMENTS=ROOT_URL + "finance/supply_payments.php";
    public static final String URL_SUPPLY_PAYMENTS2=ROOT_URL + "finance/supply_payments2.php";
    public static final String URL_PAY_SUPPLIER=ROOT_URL + "finance/pay_supplier.php";

    //shipping mrg
    public static final String URL_ORDERS_TO_SHIP=ROOT_URL + "ship_mrg/orders_to_ship.php";
    public static final String URL_GET_DRIVERS=ROOT_URL + "ship_mrg/get_drivers.php";
    public static final String URL_SHIP_ORDER=ROOT_URL + "ship_mrg/ship_order.php";
    public static final String URL_SHIPPING_ORDERS=ROOT_URL + "ship_mrg/shipping_orders.php";
    public static final String URL_APPROVE_TENDER=ROOT_URL + "ship_mrg/approve_tender.php";


    //Service   Manager
    public static final String URL_QUOTATION_REQUEST=ROOT_URL + "serv_mrg/quot_requests.php";
    public static final String URL_QUOTATION_ITEMS=ROOT_URL + "quot_items.php";
    public static final String URL_GET_TRAINERS=ROOT_URL + "serv_mrg/get_trainers.php";
    public static final String URL_ASSIGN_TRAINER=ROOT_URL + "serv_mrg/assign_trainer.php";
    public static final String URL_COMPLETED_HISTORY=ROOT_URL + "serv_mrg/graduated.php";

    public static final String URL_COMPLETED_TRAINEES=ROOT_URL + "serv_mrg/completed_trainee.php";
    public static final String URL_APPROVE_LICENCE=ROOT_URL + "serv_mrg/approve_certificate.php";

    //technician
    public static final String URL_GET_ASSIGNED_SITES=ROOT_URL + "technician/assigned_orders.php";
    public static final String URL_GET_ASSIGNED_SERVICES=ROOT_URL + "technician/assigned_services.php";
    public static final String URL_SEND_QUOTATION=ROOT_URL + "technician/send_quotation.php";
    public static final String URL_CONFIRM_COMPLETION=ROOT_URL + "technician/confirm_completion.php";

    // Driver
    public static final String URL_GET_ASSIGNED_ORDERS=ROOT_URL + "driver/assigned_orders.php";
    public static final String URL_GET_MARKED_ORDERS=ROOT_URL + "driver/arrived_orders.php";
    public static final String URL_GET_DELIVERED_ORDERS=ROOT_URL + "driver/delivered_orders.php";
    public static final String URL_MARK_ORDER=ROOT_URL + "driver/mark_arrived.php";
    //Store mrg
    public static final String URL_GET_STOCK=ROOT_URL + "stock_mrg/stock.php";
    public static final String URL_ADD_STOCK=ROOT_URL + "stock_mrg/add_stock.php";
    public static final String URL_SUPPLIER=ROOT_URL + "stock_mrg/suppliers.php";
    public static final String URL_REQUESTMATERIALS=ROOT_URL + "stock_mrg/material_request.php";
    public static final String URL_APPROVE_MATERIALS=ROOT_URL + "ship_mrg/approve_materials.php";

    public static final String URL_GET_STOCK_LIST=ROOT_URL + "stock_mrg/stock_list.php";
    public static final String URL_REQUEST_STOCK=ROOT_URL + "stock_mrg/send_requests.php";
    public static final String URL_REQUESTS=ROOT_URL + "stock_mrg/request.php";

    public static final String URL_SUBMIT_PRODUCTION=ROOT_URL + "stock_mrg/submit_production.php";
    public static final String URL_COMPLETED_PRODUCTION=ROOT_URL + "stock_mrg/completed_production.php";
    public static final String URL_ADD_NEW_STOCK=ROOT_URL + "stock_mrg/add_new_stock.php";
    //Supervisor
    public static final String URL_PENDING_PRODUCTION=ROOT_URL + "supervisor/pending_production.php";
    public static final String URL_GET_PRODUCTION_TECH=ROOT_URL + "supervisor/get_production_tech.php";
    public static final String URL_ASSIGN_PRODUCTION=ROOT_URL + "supervisor/assign_production.php";
    public static final String URL_TRACK_PRODUCTION=ROOT_URL + "supervisor/track_production.php";
    public static final String URL_APPROVE_COMPLETION=ROOT_URL + "supervisor/confirm_completion.php";
    //Production Tech
    public static final String URL_ASSIGNED_PRODUCTION=ROOT_URL + "production_tech/assigned_production.php";
    public static final String URL_START_PRODUCTION=ROOT_URL + "production_tech/start_production.php";
    public static final String URL_COMPLETE_PRODUCTION=ROOT_URL + "production_tech/complete_production.php";
    //Trainee
    public static final String URL_GET_MY_GRADES=ROOT_URL + "client/my_grade.php";
    public static final String URL_CLASS_ATTENDANCE=ROOT_URL + "client/class_attendance.php";
    public static final String URL_SIGN_ATTENDANCE=ROOT_URL + "client/sign_attendance.php";
    public static final String URL_EXAM_QUIZ=ROOT_URL + "client/exam_quiz.php";
    public static final String URL_SUBMIT_SCORE=ROOT_URL + "client/submit_exam.php";
    public static  final String URL_CERTIFICATE_DETAILS=ROOT_URL+"client/certificate_details.php";
    public static  final String URL_BOOKING_HISTORY=ROOT_URL+"client/booking_history.php";
    public static  final String URL_SUBMIT_RATING=ROOT_URL+"client/submit_rating.php";

    //Trainer
    public static final String URL_ASSIGNED_TRAINEES = ROOT_URL + "trainer/assigned_trainees.php/";
    public static final String URL_UNITS=ROOT_URL + "trainer/units.php";
    public static final String URL_POST_PDF_TUTORIAL = ROOT_URL + "trainer/post_pdf_tutorial.php/";
    public static final String URL_POST_VIDEO_TUTORIAL = ROOT_URL + "trainer/post_video_tutorial.php/";
    public static final String URL_SUBJECT_PDF_TUTORIAL = ROOT_URL + "trainer/unit_pdf_tutorial.php/";
    public static final String ROOT_URL_PDF =ROOT_URL+ "upload_pdf/";
    public static final String URL_UNIT_VIDEO_TUTORIAL = ROOT_URL + "trainer/unit_video_tutorial.php/";
    public static final String ROOT_URL_VIDEOS =ROOT_URL+ "upload_videos/";
    public static final String URL_POSTED_ASSIGNMENTS = ROOT_URL + "trainer/posted_assignments.php/";
    public static final String ROOT_URL_ASSIGNMENTS =ROOT_URL+ "upload_assignments/";
    public static final String URL_SUBMIT_ASSIGNMENT = ROOT_URL + "client/submit_assignment.php/";
    public static final String URL_POST_ASSIGNMENT = ROOT_URL + "trainer/post_assignment.php/";
    public static final String URL_SUBMITTED_ASSIGNMENTS = ROOT_URL + "trainer/submitted_assignments.php/";
    public static final String URL_SUBMIT_ASSIGNMENT_MARKS = ROOT_URL + "trainer/submit_assign_marks.php/";
    public static final String ROOT_URL_SUBMITTED_ASSIGNMENTS =ROOT_URL+ "submited_assignments/";
    public static final String URL_EXAM_STATUS = ROOT_URL + "trainer/exam_status.php/";
    public static final String URL_UNLOCK_EXAM = ROOT_URL + "trainer/unlock_exam.php/";
    public static final String URL_LOCK_EXAM = ROOT_URL + "trainer/lock_exam.php/";
    public static final String URL_QUESTIONS_LIST=ROOT_URL + "trainer/all_questions.php";
    public static final String URL_SUBMIT_SELECTED_QUESTIONS = ROOT_URL + "trainer/submit_selected_questions.php/";
    public static final String URL_GET_GRADES = ROOT_URL + "trainer/grading.php/";
    public static final String URL_SUBMIT_TRAINEE_MARKS = ROOT_URL + "trainer/submit_trainee_marks.php/";
    public static final String URL_ACTIVATE_ATTENDANCE = ROOT_URL + "trainer/activate_attendance.php/";
    public static final String URL_ATTENDANCE_SESSIONS = ROOT_URL + "trainer/attendance_sessions.php/";
    public static final String URL_VIEW_ATTENDANCE_LIST = ROOT_URL + "trainer/attendance_list.php/";
}
