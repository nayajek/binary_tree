package com.keyin.binary_tree.repositories;

import com.keyin.binary_tree.models.TreeView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeViewRepository extends JpaRepository<TreeView, Integer> {
    boolean existsByValue(int value);
}

