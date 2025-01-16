package com.skiffboy.mine.admin.config;

import org.apache.tika.Tika;
import org.dromara.x.file.storage.core.file.ByteFileWrapperAdapter;
import org.dromara.x.file.storage.core.tika.ContentTypeDetect;
import org.dromara.x.file.storage.core.tika.TikaContentTypeDetect;
import org.dromara.x.file.storage.core.tika.TikaFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TikaFactoryConfig {

    @Bean
    public TikaFactory tikaFactory() {
        return new DefaultTikaFactory();
    }

    @Bean
    public ContentTypeDetect contentTypeDetect(TikaFactory tikaFactory) {
        return new TikaContentTypeDetect(tikaFactory);
    }

    @Bean
    public ByteFileWrapperAdapter byteFileWrapperAdapter(ContentTypeDetect contentTypeDetect) {
        return new ByteFileWrapperAdapter(contentTypeDetect);
    }

}

class DefaultTikaFactory implements TikaFactory {
    private volatile Tika tika;

    @Override
    public Tika getTika() {
        if (tika == null) {
            synchronized (this) {
                if (tika == null) {
                    tika = new Tika();
                }
            }
        }
        return tika;
    }

}

