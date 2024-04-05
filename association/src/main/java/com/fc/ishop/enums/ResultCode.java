package com.fc.ishop.enums;

/**
 * 返回状态码
 * 第一位 1:商品；2:用户；3:交易,4:促销,5:店铺,6:页面,7:设置,8:其他
 *
 * @author Chopper
 * @date 2020/4/8 1:36 下午
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "失败"),

    /**
     * 失败返回码
     */
    DEMO_SITE_EXCEPTION(4001, "演示站点禁止使用"),
    /**
     * 参数异常
     */
    PARAMS_ERROR(4002, "参数异常"),

    /**
     * OOS
     */
    OSS_EXCEPTION(80202, "文件上传失败，请稍后重试"),
    OSS_FILE_ERROR(80202, "文件格式不对，请重试"),
    OSS_DEL_FILE_ERROR(80202, "文件删除失败，请重试"),
    /**
     * 分类
     */
    CATEGORY_NOT_EXIST(10001, "商品分类不存在"),
    CATEGORY_NAME_IS_EXIST(10002, "该分类名称已存在"),
    CATEGORY_PARENT_NOT_EXIST(10003, "该分类名称已存在"),
    CATEGORY_BEYOND_THREE(10004, "最多为三级分类,添加失败"),
    CATEGORY_HAS_CHILDREN(10005, "此类别下存在子类别不能删除"),
    CATEGORY_HAS_GOODS(10006, "此类别下存在商品不能删除"),
    CATEGORY_SAVE_ERROR(10007, "此类别下存在商品不能删除"),
    CATEGORY_PARAMETER_SAVE_ERROR(10008, "分类绑定参数组添加失败"),
    CATEGORY_PARAMETER_UPDATE_ERROR(10009, "分类绑定参数组添加失败"),

    /**
     * 商品
     */
    GOODS_NOT_EXIST(11001, "商品已下架"),
    GOODS_NAME_ERROR(11002, "商品名称不正确，名称应为2-50字符"),
    GOODS_UNDER_ERROR(11003, "商品下架失败"),
    GOODS_UPPER_ERROR(11004, "商品上架失败"),
    GOODS_AUTH_ERROR(11005, "商品审核失败"),
    POINT_GOODS_ERROR(11006, "积分商品业务异常，请稍后重试"),

    /**
     * 参数
     */
    PARAMETER_SAVE_ERROR(12001, "参数添加失败"),
    PARAMETER_UPDATE_ERROR(12002, "参数编辑失败"),

    /**
     * 规格
     */
    SPEC_SAVE_ERROR(13001, "规格修改失败"),
    SPEC_UPDATE_ERROR(13002, "规格修改失败"),
    SPEC_DELETE_ERROR(13003, "此规格已绑定分类不允许删除"),

    /**
     * 品牌
     */
    BRAND_SAVE_ERROR(14001, "品牌添加失败"),
    BRAND_UPDATE_ERROR(14002, "品牌修改失败"),
    BRAND_DISABLE_ERROR(14003, "品牌禁用失败"),
    BRAND_DELETE_ERROR(14004, "品牌删除失败"),

    /**
     * 用户
     */
    USER_EDIT_SUCCESS(20001, "用户修改成功"),
    USER_NOT_EXIST(20002, "用户不存在"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    USER_AUTH_EXPIRED(20004, "用户已退出，请重新登录"),
    USER_AUTHORITY_ERROR(20005, "权限不足"),
    USER_CONNECT_LOGIN_ERROR(20006, "未找到登录信息"),
    USER_NAME_EXIST(20007, "该用户名已被注册"),
    USER_PHONE_EXIST(20008, "该手机号已被注册"),
    USER_PHONE_NOT_EXIST(20009, "手机号不存在"),
    USER_PASSWORD_ERROR(20010, "密码不正确"),
    USER_NOT_PHONE(20011, "非当前用户的手机号"),
    USER_EDIT_ERROR(20015, "用户修改失败"),
    USER_OLD_PASSWORD_ERROR(20016, "旧密码不正确"),
    USER_SIMILAR_PASSWORD(20016, "新密码与旧密码相同"),
    USER_TOKEN_ERROR(20017, "用户信息token转换错误"),

    /**
     * 权限
     */
    PERMISSION_DEPARTMENT_ROLE_ERROR(21001, "角色已绑定部门，请逐个删除"),
    PERMISSION_USER_ROLE_ERROR(21002, "角色已绑定管理员，请逐个删除"),
    PERMISSION_MENU_ROLE_ERROR(21003, "菜单已绑定角色，请先删除或编辑角色"),
    PERMISSION_DEPARTMENT_DELETE_ERROR(21004, "部门已经绑定管理员，请先删除或编辑管理员"),
    PERMISSION_BEYOND_TEN(21005, "最多可以设置10个角色"),
    PERMISSION_ROLE_MENU_ERROR(21006, "角色菜单更新失败"),

    /**
     * 购物车
     */
    CART_ERROR(30001, "读取结算页的购物车异常"),
    SHIPPING_NOT_APPLY(30005, "购物商品不支持当前收货地址配送"),
    /**
     * 订单
     */
    ORDER_ERROR(31001, "创建订单异常，请稍后重试"),
    ORDER_NOT_EXIST(31002, "订单不存在"),
    ORDER_DELIVERED_ERROR(31003, "订单状态错误，无法进行确认收货"),
    ORDER_UPDATE_PRICE_ERROR(31004, "已支付的订单不能修改金额"),
    ORDER_LOGISTICS_ERROR(31005, "物流错误"),
    ORDER_DELIVER_ERROR(31006, "物流错误"),
    ORDER_NOT_USER(31007, "非当前会员的订单"),
    ORDER_TAKE_ERROR(31008, "当前订单无法核销"),
    MEMBER_ADDRESS_NOT_EXIST(31009, "订单无收货地址，请先配置收货地址"),



    /**
     * 店铺
     */
    STORE_NOT_EXIST(50001, "此店铺不存在"),
    STORE_NAME_EXIST_ERROR(50002, "店铺名称已存在!"),
    STORE_APPLY_DOUBLE_ERROR(50003, "已有店铺，无需重复申请!"),

    /**
     * 结算单
     */
    BILL_CHECK_ERROR(51001, "只有已出账结算单可以核对"),
    BILL_COMPLETE_ERROR(51002, "只有已审核结算单可以支付"),



    /**
     * 页面
     */
    PAGE_NOT_EXIST(61001, "页面不存在"),
    PAGE_OPEN_DELETE_ERROR(61002, "当前页面为开启状态，无法删除"),
    PAGE_DELETE_ERROR(61003, "当前页面为唯一页面，无法删除"),
    PAGE_RELEASE_ERROR(61004, "页面已发布，无需重复提交"),

    /**
     * 设置
     */
    SETTING_NOT_TO_SET(70001, "该参数不需要设置"),
    SETTING_MENU_ERROR(70002, "添加菜单失败"),

    /**
     * 验证码
     */
    VERIFICATION_SEND_SUCCESS(80301, "短信验证码,发送成功"),
    VERIFICATION_ERROR(80302, "验证失败"),
    VERIFICATION_SMS_ERROR(80303, "短信验证码错误，请重新校验"),
    VERIFICATION_SMS_EXPIRED_ERROR(80304, "验证码已失效，请重新校验"),

    ;
    private final Integer code;
    private final String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
