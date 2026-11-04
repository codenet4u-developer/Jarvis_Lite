 
public class TestLauncher {

    public static void main(String[] args) {

        AppLauncher launcher = new AppLauncher();

        System.out.println(
            launcher.openApplication("notepad")
        );

        System.out.println(
            launcher.openApplication("calculator")
        );

        System.out.println(
            launcher.openWebsite("youtube")
        );
    }
}
