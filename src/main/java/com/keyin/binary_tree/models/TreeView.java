package com.keyin.binary_tree.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public final class TreeView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int value;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "left_id")
    @JsonManagedReference //Forward reference for left child
    private TreeView left;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "right_id")
    @JsonManagedReference //Forward reference for right child
    private TreeView right;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference //Back reference for parent to prevent circular references
    private TreeView parent;

    // Default constructor
    public TreeView() {}

    // Constructor for convenience
    public TreeView(int value, TreeView left, TreeView right, TreeView parent) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeView getLeft() {
        return left;
    }

    public void setLeft(TreeView left) {
        this.left = left;
    }

    public TreeView getRight() {
        return right;
    }

    public void setRight(TreeView right) {
        this.right = right;
    }

    public TreeView getParent() {
        return parent;
    }

    public void setParent(TreeView parent) {
        this.parent = parent;
    }
}
