package de.jeha.s3srv.storage;

import java.io.InputStream;

/**
 * @author jenshadlich@googlemail.com
 */
public interface InputStreamCallback {

    InputStream getInputStream();

}
