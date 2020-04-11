import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import perishing.constraint.language.support.LanguageSupport;

public class TranslationBundleTest {

    @Test
    @DisplayName("加载TestText.properties资源文件")
    public void loadTestBundleTest() {
        TestText testText = LanguageSupport.getBundleFor(TestText.class);

        Assertions.assertEquals(testText.hello, "hello");
        Assertions.assertEquals(testText.world, "world");
    }

}
