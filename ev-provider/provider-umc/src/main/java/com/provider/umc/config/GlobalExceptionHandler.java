//package com.provider.umc.config;
//
//
//import com.ev.common.base.dto.JsonResult;
//import org.hibernate.validator.internal.engine.path.PathImpl;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.ValidationException;
//import java.util.Set;
//
//@ControllerAdvice
//@Order( value = Ordered.LOWEST_PRECEDENCE )
//public class GlobalExceptionHandler {
//
//
//
//    @ExceptionHandler(ValidationException.class)
//    @ResponseBody
//    public JsonResult badArgumentHandler(ValidationException e) {
//        e.printStackTrace();
//        if (e instanceof ConstraintViolationException) {
//            ConstraintViolationException exs = (ConstraintViolationException) e;
//            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
//            for (ConstraintViolation<?> item : violations) {
//                String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
//                return JsonResult.error(message);
//            }
//        }
//        return JsonResult.error("参数错误");
//    }
//
//    /**
//     * 所有验证框架异常捕获处理
//     * @return
//     */
//    @ResponseBody
//    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
//    public JsonResult validationExceptionHandler(Exception exception) {
//        BindingResult bindResult = null;
//        if (exception instanceof BindException) {
//            bindResult = ((BindException) exception).getBindingResult();
//        } else if (exception instanceof MethodArgumentNotValidException) {
//            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
//        }
//        String msg;
//        if (bindResult != null && bindResult.hasErrors()) {
//            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
//            if (msg.contains("NumberFormatException")) {
//                msg = "参数类型错误！";
//            }
//        }else {
//            msg = "系统繁忙，请稍后重试...";
//        }
//        return JsonResult.error(msg);
//    }
//
//
//
//
//    @ExceptionHandler({Exception.class})
//    @ResponseBody
//    public JsonResult resolverCustomerException(HttpServletRequest request, Exception ex){
//        ex.printStackTrace();
//        return JsonResult.error(ex.getMessage());
//    }
//}
