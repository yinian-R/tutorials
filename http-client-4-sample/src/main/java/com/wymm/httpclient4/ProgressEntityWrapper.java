package com.wymm.httpclient4;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 将 HttpEntityWrapper 扩展为 ProgressEntityWrapper，我们重写 writeTo() 方法以使用 {@link CountingOutputStream}
 */
public class ProgressEntityWrapper extends HttpEntityWrapper {
    
    private ProgressListener progressListener;
    
    public ProgressEntityWrapper(HttpEntity wrappedEntity, ProgressListener listener) {
        super(wrappedEntity);
        this.progressListener = listener;
    }
    
    @Override
    public void writeTo(OutputStream outStream) throws IOException {
        super.writeTo(new CountingOutputStream(outStream, progressListener, getContentLength()));
    }
    
    
    public static interface ProgressListener {
        void progress(float percentage);
    }
    
    /**
     * 将 FilterOutputStream 扩展为 CountingOutputStream，我们重写 write() 方法以计算已写入的字节
     */
    public static class CountingOutputStream extends FilterOutputStream {
        private ProgressListener listener;
        private long transferred;
        private float totalBytes;
        
        public CountingOutputStream(OutputStream out, ProgressListener listener, float totalBytes) {
            super(out);
            this.listener = listener;
            transferred = 0;
            this.totalBytes = totalBytes;
        }
        
        @Override
        public void write(int b) throws IOException {
            super.write(b);
            transferred++;
            listener.progress(getCurrentProgress());
        }
        
        private float getCurrentProgress() {
            return (transferred / totalBytes) * 100;
        }
    }
}

