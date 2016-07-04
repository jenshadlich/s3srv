package de.jeha.s3srv.common;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertTrue;

/**
 * @author jenshadlich@googlemail.com
 */
public class BucketNameValidatorTest {

    @Test
    public void testValidBucketNames() {
        assertTrue(BucketNameValidator.isValid("buc"));
        assertTrue(BucketNameValidator.isValid("mybucket"));
        assertTrue(BucketNameValidator.isValid("my.bucket"));
        assertTrue(BucketNameValidator.isValid("mybucket.1"));
        assertTrue(BucketNameValidator.isValid("my-bucket"));
    }

    @Test
    public void testInvalidNames() {
        // too short
        assertThatThrownBy(() -> BucketNameValidator.isValid("b")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid("bu")).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> BucketNameValidator.isValid(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid("MyBucket")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid("192.168.5.4")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid(".mybucket")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid("mybucket.")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BucketNameValidator.isValid("my..bucket")).isInstanceOf(IllegalArgumentException.class);
    }

}
