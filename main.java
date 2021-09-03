import java.util.Scanner;
import java.util.Map.Entry;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.List;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.matt.blake.Person;
import com.matt.blake.PersonFactory;
import com.google.gson.Gson;
import com.matt.blake.Athlete;
import com.matt.blake.Musician;

class Application {
    static String operation;
    static String personType;
    static Gson gson;

    private static String getTypeOfPerson(Scanner sc) {
        System.out.print("What type of person do you need to enter (Musician or Athlete): ");
        return sc.nextLine();
    }

    private static String getOperation(Scanner sc) {
        System.out.print("What operation? Put, Post, Get or Delete or Exit : ");
        return sc.nextLine();
    }

    private static String[] getPersonDetails(Scanner sc) {
        String input = "";

        if (personType.equalsIgnoreCase("Musician")) {
            System.out.print("Please enter: Name, sex, birthdate mm/dd/yyyy, ssn, instrument : ");
            input = sc.nextLine();
        }
        if (personType.equalsIgnoreCase("Athlete")) {
            System.out.print("Please enter: Name, sex, birthdate mm/dd/yyyy, ssn, sport : ");
            input = sc.nextLine();
        }
        return input.split("[,]", 0);
    }

    private static String getPersonSSN(Scanner sc) {
        String input = "";
        System.out.print("Please enter: ssn: ");
        input = sc.nextLine();

        return input;
    }

    private static void changeFromPeople(Person thePerson, Scanner sc) {
        try {

            // if (cache.get(jSonStr.hashCode()) != null) {
            // update the object
            // remove from the map
            // add it back.
            System.out.println("What would you like to change attribute:value");
            String input = sc.nextLine();
            String[] inputs = input.split("[:]", 0);

            if (personType.equalsIgnoreCase("Athlete")) {

                if (inputs[0].equalsIgnoreCase("sex")) {
                    thePerson.setSex(inputs[1]);
                } else if (inputs[0].equalsIgnoreCase("name")) {
                    thePerson.setName(inputs[1]);
                } else if (inputs[0].equalsIgnoreCase("sport")) {
                    ((Athlete) thePerson).setSport(inputs[1]);
                }

            } else if (personType.equalsIgnoreCase("Musician")) {

                if (inputs[0].equalsIgnoreCase("sex")) {
                    thePerson.setSex(inputs[1]);
                } else if (inputs[0].equalsIgnoreCase("name")) {
                    thePerson.setName(inputs[1]);
                } else if (inputs[0].equalsIgnoreCase("instrument")) {
                    ((Musician) thePerson).setInstrument(inputs[1]);
                }

            }
            System.out.println(thePerson.toString());

        } catch (Exception e) {
            System.out.println("Exception found in get from cache." + e);
        }
    }

    private static void checkInCache(String jSonStr, LinkedHashMap<Integer, String> cache, Scanner sc) {
        if (cache.get(jSonStr.hashCode()) != null) {
            System.out.println(cache.get(jSonStr.hashCode()) + "Exists in the cache.");
        } else {
            System.out.println("Not found in the Cache.");
        }
    }

    private static void removeFromCache(String jSonStr, LinkedHashMap<Integer, String> cache, Scanner sc) {

    }

    public static void main(String args[]) throws IOException {
        // The only thing this main program knows to do is respond to post, put, get and
        // delete
        // responses. It will recieve an object type and the factory pattern will create
        // objects
        //
        final String propsFilePath = "/Users/Matt/stores/data.properties";
        Properties properties = new Properties();
        LinkedHashMap<Integer, String> cache = new LinkedHashMap<Integer, String>(20, (float) .95, true);
        PersonFactory personCreator = new PersonFactory();
        HashMap<String, Person> personStore = new HashMap<String, Person>();
        Gson gson = new Gson();
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
            operation = getOperation(sc);
            if (operation.equalsIgnoreCase("Exit"))
                break;
            if (operation.equals("Post")) {
                personType = getTypeOfPerson(sc);
                if (personType.equalsIgnoreCase("Musician")) {
                    String[] inputs = getPersonDetails(sc);
                    personCreator.setPersonType(personType);
                    Person newOne = personCreator.getPerson();
                    newOne.setName(inputs[0]);
                    newOne.setSex(inputs[1]);
                    newOne.setBirthdate(inputs[2]);
                    newOne.setSSN(inputs[3]);
                    ((Musician) newOne).setInstrument(inputs[4]);
                    newOne.setCache(cache);
                    newOne.setJson(gson.toJson(newOne));
                    newOne.store();
                    personStore.put(newOne.getSSN(), newOne);
                } else if (personType.equals("Athlete")) {
                    String[] inputs = getPersonDetails(sc);
                    personCreator.setPersonType(personType);
                    Person newOne = personCreator.getPerson();
                    newOne.setName(inputs[0]);
                    newOne.setSex(inputs[1]);
                    newOne.setBirthdate(inputs[2]);
                    newOne.setSSN(inputs[3]);
                    ((Athlete) newOne).setSport(inputs[4]);
                    newOne.setCache(cache);
                    newOne.setJson(gson.toJson(newOne));
                    newOne.store();
                    // System.out.println("SSN --> " + newOne.getSSN());
                    personStore.put(newOne.getSSN(), newOne);
                }
            } else if (operation.equals("Put")) {
                personType = getTypeOfPerson(sc);
                if (personType.equalsIgnoreCase("Athlete")) {
                    String userSSN = getPersonSSN(sc);
                    try {
                        // System.out.println("SSN --> " + userSSN);
                        Person updatePerson = (Athlete) personStore.get(userSSN);
                        if (updatePerson == null) {
                            System.out.println("No person found.");
                        } else {
                            changeFromPeople(updatePerson, sc);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else if (personType.equalsIgnoreCase("Musician")) {
                    String userSSN = getPersonSSN(sc);
                    try {
                        // System.out.println("SSN --> " + userSSN);
                        Person updatePerson = (Musician) personStore.get(userSSN);
                        if (updatePerson == null) {
                            System.out.println("No person found.");
                        } else {
                            changeFromPeople(updatePerson, sc);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } else if (operation.equals("Get")) {
                personType = getTypeOfPerson(sc);
                String userSSN = getPersonSSN(sc);

                if (personType.equalsIgnoreCase("Athlete")) {
                    // System.out.println("SSN --> " + userSSN);
                    Person updatePerson = (Athlete) personStore.get(userSSN);
                    if (updatePerson == null) {
                        System.out.println("No person found.");
                    } else {
                        System.out.println(updatePerson.toString());
                    }
                    /*
                     * String[] inputs = getPersonDetails(sc);
                     * personCreator.setPersonType(personType); Person newOne =
                     * personCreator.getPerson(); newOne.setName(inputs[0]);
                     * newOne.setSex(inputs[1]); newOne.setBirthdate(inputs[2]);
                     * newOne.setSSN(inputs[3]); ((Athlete) newOne).setSport(inputs[4]);
                     * newOne.setCache(cache); String jSonStr = newOne.retrieve(newOne);
                     * checkInCache(jSonStr, cache, sc);
                     */
                } else if (personType.equalsIgnoreCase("Musician")) {
                    // System.out.println("SSN --> " + userSSN);
                    Person updatePerson = (Musician) personStore.get(userSSN);
                    if (updatePerson == null) {
                        System.out.println("No person found.");
                    } else {
                        System.out.println(updatePerson.toString());
                    }
                    /*
                     * String[] inputs = getPersonDetails(sc);
                     * personCreator.setPersonType(personType); Person newOne =
                     * personCreator.getPerson(); newOne.setName(inputs[0]);
                     * newOne.setSex(inputs[1]); newOne.setBirthdate(inputs[2]);
                     * newOne.setSSN(inputs[3]); ((Musician) newOne).setInstrument(inputs[4]);
                     * newOne.setCache(cache); newOne.setJson(gson.toJson(newOne)); newOne.store();
                     * String jSonStr = newOne.retrieve(newOne); checkInCache(jSonStr, cache, sc);
                     */
                }
            } else if (operation.equals("Delete")) {
                personType = getTypeOfPerson(sc);
                if (personType.equalsIgnoreCase("Athlete")) {
                    String[] inputs = getPersonDetails(sc);
                    personCreator.setPersonType(personType);
                    Person newOne = personCreator.getPerson();
                    newOne.setName(inputs[0]);
                    newOne.setSex(inputs[1]);
                    newOne.setBirthdate(inputs[2]);
                    newOne.setSSN(inputs[3]);
                    ((Athlete) newOne).setSport(inputs[4]);
                    newOne.setCache(cache);
                    String jSonStr = newOne.retrieve(newOne);
                    removeFromCache(jSonStr, cache, sc);
                } else if (personType.equalsIgnoreCase("Musician")) {
                    String[] inputs = getPersonDetails(sc);
                    personCreator.setPersonType(personType);
                    Person newOne = personCreator.getPerson();
                    newOne.setName(inputs[0]);
                    newOne.setSex(inputs[1]);
                    newOne.setBirthdate(inputs[2]);
                    newOne.setSSN(inputs[3]);
                    ((Musician) newOne).setInstrument(inputs[4]);
                    newOne.setCache(cache);
                    newOne.setJson(gson.toJson(newOne));
                    newOne.store();
                    String jSonStr = newOne.retrieve(newOne);
                    removeFromCache(jSonStr, cache, sc);
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
