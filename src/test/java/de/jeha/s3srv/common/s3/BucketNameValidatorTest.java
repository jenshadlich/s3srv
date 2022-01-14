package de.jeha.s3srv.common.s3;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testInvalidNamesThatAreTooShort() {

        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("b"));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("bu"));
    }

    @Test
    public void testInvalidNames() {
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid(null));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("MyBucket"));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("192.168.5.4"));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid(".mybucket"));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("mybucket."));
        assertThrows(ValidationException.class, () -> BucketNameValidator.isValid("my..bucket"));
    }

}
