package com.waterelephant.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具
 *
 * @author Hui Wang
 */
public class PageUtil {

    public static int PAGE_SIZE = 30;

    public static int[] init(HttpServletRequest request) {
        String pageNoStr = request.getParameter("pageCurrent");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNo = CommUtils.isNull(pageNoStr) ? 1 : Integer.parseInt(pageNoStr);
        int pageSize = CommUtils.isNull(pageSizeStr) ? PageUtil.PAGE_SIZE : Integer.parseInt(pageSizeStr);
        return new int[]{pageNo, pageSize};
    }
}
