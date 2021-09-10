import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"JohnDoe", "123456", "12345678911131517192123252729X32", "!§$%&/()={}≠„^^^2"})
    void validCandidates(String pwString) {
        //given
        Password pw = new Password(pwString);
        //when
        boolean actualValidity = pw.isValid();

        //then
        Assertions.assertTrue(actualValidity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"blank space", "tshrt", "123456789111315171921232527293133"})
    void invalidCandidates(String pwString) {
        //given
        Password pw = new Password(pwString);
        //when
        boolean actualValidity = pw.isValid();

        //then
        Assertions.assertFalse(actualValidity);
    }

    @Test
    void elementwiseValidityArrayValidCandidates() {
        //given
        Password[] input = new Password[]{
                                            new Password("blank space"),
                                            new Password("tshrt"),
                                            new Password("123456789111315171921232527293133"),
                new Password("JohnDoe"),
                new Password("123456"),
                new Password("12345678911131517192123252729X32"),
                new Password("!§$%&/()={}≠„^^^2")};
        boolean[] actualValidity;
        actualValidity = Password.elementwiseValidityArray(input);
        boolean[] expectedValidity = {false,false,false,true,true,true,true};
    }


}