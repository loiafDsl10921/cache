import java.util.Scanner;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.matt.blake.Person;
import com.google.gson.Gson;

class app {

    public static void main(String args[]) throws IOException {

        final String propsFilePath = "/Users/Matt/stores/data.properties";
        Properties properties = new Properties();
        LinkedHashMap<Integer, String> cache = new LinkedHashMap<Integer, String>(20, (float) .95, true);

        try {
            properties.load(new FileInputStream(propsFilePath));

            for (Entry<Object, Object> propsLoop : properties.entrySet()) {
                cache.put(propsLoop.getValue().hashCode(), (String) propsLoop.getValue());
            }

        } catch (Exception E) {
            System.out.println("No file yet.");
            System.out.println(E.toString());
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("What operation? Put, Post, Get or Delete or Exit : ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("Exit"))
                break;

            if (input.equals("Post")) {
                System.out.print("Please enter: Name, sex, age : ");
                input = sc.nextLine();

                String[] inputs = input.split("[,]", 0);
                Person newOne = new Person(inputs[0], inputs[1], inputs[2]);
                Gson gson = new Gson(); // Or use new GsonBuilder().create();

                String newOneJson = gson.toJson(newOne); // serializes target to Json

                if (cache.get(newOneJson.hashCode()) == null) {
                    cache.put(newOneJson.hashCode(), newOneJson);
                    System.out.println(cache.get(newOneJson.hashCode()) + "Added to the cache.");
                } else {
                    System.out.println(cache.get(newOneJson.hashCode()) + " - Already Exists in the Cache.");
                }
            } else if (input.equals("Put")) {
                System.out.print("Please enter original version: Name, sex, age : ");
                input = sc.nextLine();

                String[] inputs = input.split("[,]", 0);
                Person newOne = new Person(inputs[0], inputs[1], inputs[2]);
                Gson gson = new Gson(); // Or use new GsonBuilder().create();

                String newOneJson = gson.toJson(newOne); // serializes target to Json

                if (cache.get(newOneJson.hashCode()) != null) {

                    Person matchPerson = gson.fromJson(newOneJson, Person.class); // deserializes json into target2

                    System.out.print("Please enter new version: Name, sex, age : ");
                    input = sc.nextLine();

                    inputs = input.split("[,]", 0);
                    String newName = inputs[0];
                    String newSex = inputs[1];
                    String newAge = inputs[2];

                    matchPerson.setName(newName);
                    matchPerson.setSex(newSex);
                    matchPerson.setAge(newAge);

                    newOneJson = gson.toJson(matchPerson); // serializes target to Json

                    cache.put(newOneJson.hashCode(), newOneJson);

                    System.out.println(cache.get(newOneJson.hashCode()) + "Added to the cache.");

                } else {
                    System.out.println("Not found in the Cache.");
                }

            } else if (input.equals("Get")) {
                System.out.print("Please enter: Name, sex, age : ");
                input = sc.nextLine();

                String[] inputs = input.split("[,]", 0);
                Person newOne = new Person(inputs[0], inputs[1], inputs[2]);
                Gson gson = new Gson(); // Or use new GsonBuilder().create();

                String newOneJson = gson.toJson(newOne); // serializes target to Json

                if (cache.get(newOneJson.hashCode()) == null) {

                    System.out.println("No such person exists, please post.");
                } else {
                    System.out.println(cache.get(newOneJson.hashCode()) + " - From the Cache.");
                }
            } else if (input.equals("Delete")) {
                System.out.print("Please enter: Name, sex, age : ");
                input = sc.nextLine();

                String[] inputs = input.split("[,]", 0);
                Person newOne = new Person(inputs[0], inputs[1], inputs[2]);
                Gson gson = new Gson(); // Or use new GsonBuilder().create();

                String newOneJson = gson.toJson(newOne); // serializes target to Json

                if (cache.get(newOneJson.hashCode()) == null) {

                    System.out.println("No such person exists, please post.");
                } else {
                    System.out.println(cache.remove(newOneJson.hashCode()) + " - Removed from the Cache.");
                }
            }

        }
        sc.close();

        try {
            if (cache.get(args[0].toString().hashCode()) != null) {
                System.out.println(args[0].toString() + " is in the cache.");
            } else {
                System.out.println(args[0].toString() + " is not in this cache.");
            }
        } catch (Exception E) {
            // do nothing.
        }

        for (Entry<Integer, String> cacheIter : cache.entrySet()) {
            properties.put(cacheIter.getKey().toString(), cacheIter.getValue());
        }

        File propsFile = new File(propsFilePath);
        properties.store(new FileOutputStream(propsFile), null);
    }
}
