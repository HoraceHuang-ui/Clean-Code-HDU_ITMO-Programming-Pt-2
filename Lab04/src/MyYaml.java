import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyYaml {
    public static void saveTo(File target, DiscountDatasList dataList) throws IOException {
        FileOutputStream fs = new FileOutputStream(target);
        fs.write(dataList.toString().getBytes());
    }

    public static DiscountDatasList loadFrom(File source) throws IOException {
        DiscountDatasList res = new DiscountDatasList();
        FileInputStream fs = new FileInputStream(source);
        int n = 0;
        StringBuilder yml = new StringBuilder();
        while (n != -1) {
            n = fs.read();
            yml.append((char) n);
        }
        yml.deleteCharAt(yml.length()-1);
        /*
            YAML format:
            name: %s\n
            shop: %s\n
            discountSize: %d\n
            expirationDate: %d/%d/%d\n
            \n
            name:%s\n
            ...
         */
        for (String data : yml.toString().split("\n\n")) {
            res.add();
            String[] token = data.split("(: )|\n");
            res.edit(res.count()-1,
                token[1], token[3], Integer.parseInt(token[5]), DiscountDatasList.parseDate(token[7]));
        }
        return res;
    }
}
