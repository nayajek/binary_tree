package com.keyin.binary_tree.services;

import com.keyin.binary_tree.models.TreeView;
import com.keyin.binary_tree.repositories.TreeViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeViewService {

    private static final Logger logger = LoggerFactory.getLogger(TreeViewService.class);

    private final TreeViewRepository treeViewRepository;

    @Autowired
    public TreeViewService(TreeViewRepository treeViewRepository) {
        this.treeViewRepository = treeViewRepository;
    }

    // Create TreeView
    public TreeView createTreeView(int value, TreeView parent) {
        logger.info("Creating TreeView with value: {} and parent: {}", value, parent != null ? parent.getValue() : "None");

        // Create a new TreeView instance
        TreeView newView = new TreeView();
        newView.setValue(value);

        // Set the parent relationship if provided
        if (parent != null) {
            newView.setParent(parent);
        }

        // Save the new node and return it
        return treeViewRepository.save(newView);
    }

    public TreeView createTreeViewWithParent(int value, Integer parentId) {
        TreeView parent = null;

        // Fetch the parent view if parentId is provided
        if (parentId != null) {
            parent = treeViewRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent TreeView with ID " + parentId + " not found"));
        }

        // Create and return the new TreeView
        return createTreeView(value, parent);
    }

    // Get TreeView by ID
    public TreeView getTreeViewById(int id) {
        logger.info("Fetching TreeView with ID: {}", id);
        return treeViewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TreeView with ID " + id + " not found"));
    }

    // Get TreeView
    public List<TreeView> getAllTreeViews() {
        logger.info("Fetching all TreeViews");
        return treeViewRepository.findAll();
    }

    // Delete TreeView by ID
    public void deleteTreeView(int id) {
        logger.info("Attempting to delete TreeView with ID: {}", id);
        TreeView view = treeViewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TreeView with ID " + id + " does not exist"));
        treeViewRepository.delete(view);
        logger.info("Deleted TreeView with ID: {}", id);
    }

    public TreeView createOrInsertView(TreeView root, int value, TreeView parent) {
        if (root == null) {
            logger.info("Inserting new TreeView with value: {}", value);
            return createTreeView(value, parent);
        }
        if (value == root.getValue()) {
            throw new IllegalArgumentException("Duplicate values are not allowed in the binary search tree");
        }
        if (value < root.getValue()) {
            TreeView leftChild = createOrInsertView(root.getLeft(), value, root);
            root.setLeft(leftChild);
        } else {
            TreeView rightChild = createOrInsertView(root.getRight(), value, root);
            root.setRight(rightChild);
        }

        // Save updates to the current root view
        return treeViewRepository.save(root);
    }

    // Recursively Save the Entire Tree
    public void saveTree(TreeView root) {
        if (root != null) {
            logger.info("Saving TreeView with value: {}", root.getValue());
            saveTree(root.getLeft());
            saveTree(root.getRight());
            treeViewRepository.save(root);
        }
    }
}
