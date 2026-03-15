// package com.example.repository;

// public class StaffRepository {

//     public Object findAll() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'findAll'");
//     }

// }

package com.example.repository;

import com.example.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    // Yahan humein kuch likhne ki zaroorat nahi hai, 
    // JpaRepository apne aap saare basic kaam (Save, Find, Delete) kar lega.
}