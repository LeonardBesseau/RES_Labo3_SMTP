package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.config.ConfigManager;
import ch.heigvd.res.mail.model.mail.Group;
import ch.heigvd.res.mail.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrankGenerator {

    private ConfigManager configManager;

    public PrankGenerator(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configManager.getMessages();
        Collections.shuffle(messages);
        int index = 0;

        int nbGroup = configManager.getNbGroups();
        int nbTargets = configManager.getTargets().size();

        if (nbTargets / nbGroup < 3) {
            nbGroup = nbTargets / 3;
            System.err.printf("Not enough targets to generate enough groups. Continue using %d groups", nbGroup);
        }

        List<Group> groups = generateGroups(configManager.getTargets(), nbGroup);

        for (Group group : groups) {
            Prank prank = new Prank();
            List<Person> targets = group.getMembers();
            Collections.shuffle(targets);
            Person sender = targets.remove(0);
            prank.setSender(sender);
            prank.addTarget(targets);

            prank.addCopy(configManager.getCopy());
            String message = messages.get(index);
            index = (index + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }
        return pranks;
    }

    public List<Group> generateGroups(List<Person> targets, int nbGroup) {
        List<Person> availableTargets = new ArrayList<>(targets);
        Collections.shuffle(availableTargets);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < nbGroup; i++) {
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group targetGroup;
        while (availableTargets.size() > 0){
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person target = availableTargets.remove(0);
            targetGroup.addMember(target);
        }
        return groups;
    }
}
