package de.jeha.s3srv;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author jenshadlich@googlemail.com
 */
public class BucketNameValidatorTest {

    @Test
    public void test() {

        // valid
/*
        assertTrue(BucketNameValidator.isValid("buc"));
        assertTrue(BucketNameValidator.isValid("mybucket"));
        assertTrue(BucketNameValidator.isValid("my.bucket"));
        assertTrue(BucketNameValidator.isValid("mybucket.1"));
        assertTrue(BucketNameValidator.isValid("my-bucket"));
*/
        // not valid, too short
        assertFalse(BucketNameValidator.isValid("b"));
        assertFalse(BucketNameValidator.isValid("bu"));

        // not valid
        assertFalse(BucketNameValidator.isValid(null));
        assertFalse(BucketNameValidator.isValid(".mybucket"));
        assertFalse(BucketNameValidator.isValid("mybucket."));
        assertFalse(BucketNameValidator.isValid("my..bucket"));
    }

}
