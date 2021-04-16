package ch.heigvd.res.mail.model.prank;

import ch.heigvd.res.mail.config.IConfigManager;
import ch.heigvd.res.mail.model.mail.Group;
import ch.heigvd.res.mail.model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class  PrankGenerator {

    private IConfigManager configManager;

    public PrankGenerator(IConfigManager configManager) {
        this.configManager = configManager;
    }

    public List<Prank> generatePranks(){
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configManager.getMessages();
        int index = 0;

        int nbGroup = configManager.getNbGroups();
        int nbTargets = configManager.getTargets().size();

        if(nbTargets/nbGroup < 3){
            nbGroup = nbTargets/3;
            System.err.printf("Not enough targets to generate enough groups. Continue using %d groups", nbGroup);
        }

        List<Group> groups = GenerateGroups(configManager.getTargets(), nbGroup);

        for(Group group: groups){
            Prank prank = new Prank();
            List <Person> targets = group.getMembers();
        }
    }
}
