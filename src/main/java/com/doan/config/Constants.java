package com.doan.config;

public class Constants {
    public static final int ERR_CODE = 500;
    public static final int SUCCCES_CODE = 200;

    public static final String DELETE_ERR = "Xóa thất bại";

    public static final String DELETE_SUCCESS = "Xóa thành công";

    public static final String MESSAGE_SUCCES_ADD = "Thêm thành công";

    public static final String MESSAGE_ERR_ADD = "Thêm thất bại";

    public static final String MESSAGE_SUCCES_EDIT = "Cập nhật thành công";

    public static final String MESSAGE_ERR_EDIT = "Cập nhật thất bại";

    public static final String MESSAGE_GET_ALL_SUCCES = "Get all thành công";

    public static final String MESSAGE_GET_ALL_ERR = "Get all thất bại";

    public static final String MESSAGE_VALIDATE_LOGINID = "Trùng tài khoản đăng nhập";

    public static final String MESSAGE_VALIDATE_ENDDATE = "Ngày bắt đầu phải lớn hơn ngày kết thúc";

    public static final String MESSAGE_PCPB_SUCCES = "Đã thêm giảng viên phản biện thành công";
    public static final String MESSAGE_PCPB_ERR = "Thêm giảng viên phản biện thất bại";

    public static final String MESSAGE_ADD_COUCIL_SUCCES = "Thêm hội dồng thành công";
    public static final String MESSAGE_ADD_COUCIL_ERR = "Thêm hội dồng thất bại";

    public static final String MESSAGE_ADD_SCORE_COUCIL_SUCCES = "Thêm điểm hội dồng thành công";
    public static final String MESSAGE_ADD_SCORE_COUCIL_ERR = "Thêm điểm hội dồng thất bại";
    public static final String MESSAGE_ADD_SCORE_ARGUMENT_SUCCES = "Thêm điểm phản biện thành công";
    public static final String MESSAGE_ADD_SCORE_ARGUMENT_ERR = "Thêm điểm phản biện thất bại";
    public static final String MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES = "Lấy ra thành công";
    public static final String MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR = "Lấy ra thất bại";

    public static final String MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR_2 = "Không có giảng viên này";
    public static final String MESSAGE_ERR_EDIT_USER = "Sai tên tài khoản hoặc mật khẩu";
    public static final String MESSAGE_ERR_LOGIN= "Sai tên tài khoản hoặc mật khẩu";
    public static final String MESSAGE_SUCCES_LOGIN = "Đăng nhập thành công";
    public static final String MESSAGE_ADD_TEACHER_WITH_COUCIL_SUCCES = "Thêm giảng viên vào hội đồng thành công";
    public static final String MESSAGE_ADD_TEACHER_WITH_COUCIL_ERR = "Thêm giảng viên vào hội đồng thất bại";
    public static final String MESSAGE_EDIT_PASSWORD_SUCCES = "Đổi mật khẩu thành công";

    public static final String MESSAGE_EDIT_PASSWORD_ERR = "Sai username hoặc password";
    public static final String MESSAGE_EDIT_MASTER_DETAIL_BY_GVHD_SUCCES = "Xác nhận hướng dẫn cho sinh viên thành công";
    public static final String MESSAGE_EDIT_MASTER_DETAIL_BY_GVHD_ERR = "Xác nhận hướng dẫn cho sinh viên thất bại";
    public static final String MESSAGE_EDIT_MASTER_DETAIL_BY_DEPARTMENT_SUCCES = "Duyệt đồ án cho sinh viên thành công";

    public static final String MESSAGE_EDIT_MASTER_DETAIL_BY_DEPARTMENT_ERR = "Duyệt đồ án cho sinh viên thất bại";

    public static final String MESSAGE_GET_COUCIL_BY_STATUS_SUCCES = "Lấy ra hội đồng thành công";
    public static final String MESSAGE_GET_COUCIL_BY_STATUS_ERR = "Lấy ra hội đồng thất bại";
    public static final String MESSAGE_EDIT_COUCIL_BY_DEPARTMENT_SUCCES = "Duyệt hội đồng thành công";
    public static final String MESSAGE_EDIT_COUCIL_BY_DEPARTMENT_ERR = "Duyệt hội đồng thất bại";
    public static final String MESSAGE_GET_SUBJECT_BY_ID_SUCCES = "Lấy bộ môn thành công";
    public static final String MESSAGE_GET_SUBJECT_BY_ID_ERR = "Lấy bộ môn thất bại";
    public static final String MESSAGE_GET_SUBJECT_BY_ID_NOT_FOUND = "Không có bộ môn này";
    public static final String MESSAGE_GET_TEACHER_BY_ID_NOT_FOUND = "Không có giảng viên này";
    public static final String MESSAGE_GET_TEACHER_BY_ID_SUCCESS= "Lấy giảng viên thành công";
    public static final String MESSAGE_GET_TEACHER_BY_ID_ERR= "Lấy giảng viên thát bại";
    public static final String MESSAGE_GET_MASTER_DETAIL_Successful_Defense_SUCCCES = "Lấy ra thành công";
    public static final String MESSAGE_GET_MASTER_DETAIL_Successful_Defense_ERR = "Lấy ra thất bại";
    public static final String MESSAGE_GET_COUNT_Successful_Defense_BY_SUBJECT_ERR = "Lấy ra số lượng sinh viên thất bại";
    public static final long JWT_EXPIRATION = 160 * 60 * 60; // 7 day
    public static final String[] ATTRIBUTIES_TO_TOKEN = new String[] {
            "userId",
            "username",
            "role"
    };
    public static final String[] ENDPOINTS_PUBLIC = new String[] {
            "/",
            "/user/login/**",
            "/user/register/**",
            "/error/**"
    };
    public static final String[] ENDPOINTS_WITH_ROLE = new String[] {
            "/user/**"
    };
    public static final boolean IS_CROSS_ALLOW = true;

    public static final String JWT_SECRET = "doan";
}
