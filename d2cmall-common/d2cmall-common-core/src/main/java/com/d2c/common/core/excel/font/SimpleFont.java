package com.d2c.common.core.excel.font;

import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class SimpleFont extends BaseFont {

    public WritableCellFormat wcfTitle;
    public WritableCellFormat wcfF;// wcfF为正文格式:水平居中 垂直居中 带表格 白底黑字
    public WritableCellFormat wcfLeft;// wcfLeft为正文格式:水平居左 垂直居中 带表格
    public WritableCellFormat wcfRight;// wcfLeft为正文格式:水平居左 垂直居中 带表格
    public WritableCellFormat wr_wcfF;// wr_wcfF为正文格式:水平居中 垂直居中 带表格 红底白字
    public WritableCellFormat wo_wcfF;// wo_wcfF为正文格式:水平居中 垂直居中 带表格 橙底白字
    public WritableCellFormat title_wcfF;// title_wcfF为表格标题格式:水平居中 垂直居中 带表格 粗体
    // 蓝底黑字
    public WritableCellFormat title_wcfLeft;// title_wcfF为表格标题格式:水平居左 垂直居中 带表格
    // 粗体 蓝底黑字

    @Override
    public void initFont() throws WriteException {
        // 样式
        // 创建大字体：TAHOMA,大小为11号,粗体，非斜体, 非黑体
        WritableFont wf = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD, false);
        // wcfTitle标题:水平居左 垂直居中 无表格
        wcfTitle = new WritableCellFormat(wf);
        wcfTitle.setAlignment(Alignment.CENTRE);// 水平方向居左
        wcfTitle.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wcfTitle.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wcfTitle.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wcfTitle.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wcfTitle.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // wcfF为正文格式:水平居左 垂直居中 带表格
        WritableFont wr = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD, false);
        wcfF = new WritableCellFormat(wr);
        wcfF.setAlignment(Alignment.CENTRE);// 水平方向居中
        wcfF.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wcfF.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wcfF.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // wcfF为正文格式:水平居左 垂直居中 带表格
        wcfLeft = new WritableCellFormat(wf);
        wcfLeft.setAlignment(Alignment.LEFT);// 水平方向居左
        wcfLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wcfLeft.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wcfLeft.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wcfLeft.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wcfLeft.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        wcfRight = new WritableCellFormat(wf);
        wcfRight.setAlignment(Alignment.RIGHT);// 水平方向居右
        wcfRight.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wcfRight.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wcfRight.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wcfRight.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wcfRight.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // 创建大字体：TAHOMA,大小为11号,粗体，非斜体, 白体
        WritableFont wwf = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false);
        // wr_wcfF为正文格式:水平居中 垂直居中 带表格 红底白字
        wr_wcfF = new WritableCellFormat(wwf);
        wr_wcfF.setBackground(Colour.PALE_BLUE);
        wr_wcfF.setAlignment(Alignment.CENTRE);// 水平方向居中
        wr_wcfF.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wr_wcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wr_wcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wr_wcfF.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wr_wcfF.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // wo_wcfF为正文格式:水平居中 垂直居中 带表格 橙底白字
        wo_wcfF = new WritableCellFormat(wwf);
        wo_wcfF.setBackground(Colour.LIGHT_ORANGE);
        wo_wcfF.setAlignment(Alignment.CENTRE);// 水平方向居中
        wo_wcfF.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        wo_wcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        wo_wcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        wo_wcfF.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        wo_wcfF.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // 创建大字体：TAHOMA,大小为11号,粗体，非斜体, 黑体
        WritableFont title_wf = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.BOLD, false);
        // title_wcfF为表格标题格式:水平居中 垂直居中 带表格 粗体 蓝底黑字
        title_wcfF = new WritableCellFormat(title_wf);
        // title_wcfF.setBackground(Colour.PALE_BLUE);
        title_wcfF.setAlignment(Alignment.CENTRE);// 水平方向居中
        title_wcfF.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        title_wcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        title_wcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        title_wcfF.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        title_wcfF.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
        // title_wcfF为表格标题格式:水平居中 垂直居中 带表格 粗体 蓝底黑字
        title_wcfLeft = new WritableCellFormat(title_wf);
        title_wcfLeft.setBackground(Colour.PALE_BLUE);
        title_wcfLeft.setAlignment(Alignment.LEFT);// 水平方向居中
        title_wcfLeft.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直方向居中
        title_wcfLeft.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);// 设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
        title_wcfLeft.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);// 设置右边框线为实线
        title_wcfLeft.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);// 设置底部框线为实线
        title_wcfLeft.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);// 设置左边框线为实线
    }

}
