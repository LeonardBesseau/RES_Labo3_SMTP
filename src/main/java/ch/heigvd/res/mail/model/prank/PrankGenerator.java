package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.config.ConfigManager;
import ch.heigvd.res.mail.model.mail.Group;
import ch.heigvd.res.mail.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class for generate pranks
public class PrankGenerator {

    private final ConfigManager configManager;

    /**
     * Constructor of the class PrankGenerator
     *
     * @param configManager configuration manager
     */
    public PrankGenerator(ConfigManager configManager) {
        this.configManager = configManager;
    }

    /**
     * Method that will choose pranks from messages in the configManager and that will assign them to random groups
     *
     * @return list of pranks
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();

        // Shuffling messages to assign
        List<String> messages = configManager.getMessages();
        Collections.shuffle(messages);
        int index = 0;

        int nbGroup = configManager.getNbGroups();


        List<Group> groups = generateGroups(configManager.getTargets(), nbGroup);

        // Assigning targets and messages to groups
        for (Group group : groups) {
            Prank prank = new Prank();
            List<Person> targets = group.getMembers();
            Collections.shuffle(targets);
            Person sender = targets.remove(0);
            prank.setSender(sender);  // Choosing sender
            prank.addTarget(targets); // Choosing targets

            prank.addCopy(configManager.getCopy());
            String message = messages.get(index);
            index = (index + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }
        return pranks;
    }

    /**
     * Method that will generate groups of victims
     *
     * @param targets list of victims addresses
     * @param nbGroup number of victims groups
     * @return list of groups
     */
    public List<Group> generateGroups(List<Person> targets, int nbGroup) {
        if (targets.size() / nbGroup < 3) {
            nbGroup = targets.size() / 3;
            System.err.printf("Not enough targets to generate enough groups. Continue using %d groups", nbGroup);
        }

        List<Person> availableTargets = new ArrayList<>(targets);
        Collections.shuffle(availableTargets);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < nbGroup; i++) {
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group targetGroup;
        while (availableTargets.size() > 0) {
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person target = availableTargets.remove(0);
            targetGroup.addMember(target);
        }
        return groups;
    }
}
