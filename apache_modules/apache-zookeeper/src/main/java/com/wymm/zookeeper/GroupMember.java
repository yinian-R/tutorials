package com.wymm.zookeeper;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GroupMember implements Watcher {
    
    private final ZooKeeper zookeeper;
    private final String groupPath;
    
    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeChildrenChanged) {
            list();
        }
    }
    
    public void list() {
        try {
            List<String> members = zookeeper.getChildren(groupPath, this);
            System.out.println("Members:" + String.join(",", members));
            
            members.stream().sorted().findFirst().ifPresent(lender -> {
                System.out.println("Lender:" + lender);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void join(String memberName) {
        try {
            String path = zookeeper.create(this.groupPath + "/" + memberName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("Created: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
