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
        assertTrue(BucketNameValidator.isValid("abc"));
        assertTrue(BucketNameValidator.isValid("myawsbucket"));
        assertTrue(BucketNameValidator.isValid("my.aws.bucket"));
        assertTrue(BucketNameValidator.isValid("myawsbucket.1"));
*/
        // not valid, too short
        assertFalse(BucketNameValidator.isValid("a"));
        assertFalse(BucketNameValidator.isValid("ab"));

        // not valid
        assertFalse(BucketNameValidator.isValid(null));
        assertFalse(BucketNameValidator.isValid(".myawsbucket"));
        assertFalse(BucketNameValidator.isValid("myawsbucket."));
        assertFalse(BucketNameValidator.isValid("my..examplebucket"));
    }

}
