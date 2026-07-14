package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class FileReqDto {
    /**
     * 附件url，供应商会将该路径下的文件转存为系统中的附件。必须是https可被公网访问的地址
     */
    private String url;
    /**
     * 文件名称，如果没有填写，系统会自动生成
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;
}
