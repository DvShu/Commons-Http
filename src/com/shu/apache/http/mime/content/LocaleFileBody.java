package com.shu.apache.http.mime.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.util.Args;

public class LocaleFileBody extends AbstractContentBody {
	
	private String fileName = "LocaleFileBody";
	private long size = 0;
	private InputStream in;
	
	public LocaleFileBody(String fileName, long size, InputStream in, ContentType contentType) {
		super(contentType);
		this.fileName = fileName;
		this.size = size;
		Args.notNull(in, "Input stream");
		this.in = in;
	}
	
	public LocaleFileBody(String fileName, long size, InputStream in) {
		this(fileName, size, in, ContentType.DEFAULT_BINARY);
	}

	@Override
	public String getFilename() {
		return fileName;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		Args.notNull(out, "Output stream");
        try {
            final byte[] tmp = new byte[4096];
            int l;
            while ((l = in.read(tmp)) != -1) {
                out.write(tmp, 0, l);
            }
            out.flush();
        } finally {
            in.close();
        }
	}

	@Override
	public long getContentLength() {
		return size;
	}

	@Override
	public String getTransferEncoding() {
		return MIME.ENC_BINARY;
	}

}
