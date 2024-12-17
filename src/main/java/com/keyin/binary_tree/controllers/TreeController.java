package com.keyin.binary_tree.controllers;

import com.keyin.binary_tree.models.TreeView;
import com.keyin.binary_tree.services.TreeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tree_views")
public class TreeController {

    private final TreeViewService treeViewService;

    @Autowired
    public TreeController(TreeViewService treeViewService) {
        this.treeViewService = treeViewService;
    }

    //Create TreeView
    @PostMapping
    public ResponseEntity<TreeView> createTreeView(@RequestBody Map<String, Object> payload) {
        try {
            int value = (int) payload.get("value");
            Integer parentId = (Integer) payload.get("parentId");

            TreeView parent = null;

            // Fetch parent if parentId is provided
            if (parentId != null) {
                parent = treeViewService.getTreeViewById(parentId);
            }

            TreeView newView = treeViewService.createTreeView(value, parent);
            return ResponseEntity.status(HttpStatus.CREATED).body(newView);
        } catch (ClassCastException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/with-parent")
    public ResponseEntity<TreeView> createTreeViewWithParent(@RequestBody Map<String, Integer> payload) {
        try {
            //Extract value and parentId from the payload
            int value = payload.get("value");
            Integer parentId = payload.get("parentId"); // This can be null for root view

            TreeView newView = treeViewService.createTreeViewWithParent(value, parentId);

            // Return the created view
            return ResponseEntity.status(HttpStatus.CREATED).body(newView);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // Get a TreeView by ID
    @GetMapping("/{id}")
    public ResponseEntity<TreeView> getTreeViewById(@PathVariable int id) {
        try {
            TreeView treeView = treeViewService.getTreeViewById(id);
            return ResponseEntity.ok(treeView);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all TreeView
    @GetMapping
    public ResponseEntity<List<TreeView>> getAllTreeViews() {
        List<TreeView> allTreeView = treeViewService.getAllTreeViews();
        if (allTreeView.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allTreeView);
    }

    // Delete a TreeView by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTreeView(@PathVariable int id) {
        try {
            treeViewService.deleteTreeView(id);
            return ResponseEntity.ok("TreeView deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TreeView with ID " + id + " not found.");
        }
    }
}



