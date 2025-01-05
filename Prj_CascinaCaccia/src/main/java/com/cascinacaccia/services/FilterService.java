package com.cascinacaccia.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.UserDAO;

@Service
/*
 * FilterService provides various methods for filtering, sorting, and paginating users.
 * It offers functionality to:
 * - Retrieve all users from the database.
 * - Sort users by surname in ascending or descending order.
 * - Search users by email, name, or surname, with support for full name searches (name and surname).
 * - Paginate the results of user lists for efficient display of large datasets.
 * - Calculate total pages and determine the start and end pages for pagination.
 */
public class FilterService {
	
	@Autowired
	private UserDAO userDAO;
	
	//method to get all users from the database
    public List<User> getAllUsers() {
        return userDAO.findAll();  
    }
	
    //method to sort users by surname in ascending or descending order
    public static List<User> sortUsersBySurname(List<User> users, boolean ascending) {
        return users.stream()
                .sorted((user1, user2) -> ascending 
                    ? user1.getSurname().compareToIgnoreCase(user2.getSurname()) 
                    : user2.getSurname().compareToIgnoreCase(user1.getSurname()))
                .collect(Collectors.toList());
    }
    
	//method to search users by email or name/surname
    public List<User> searchUsers(String query) {
        
    	List<User> users = new ArrayList<>();
        
        //trim the query to remove any leading or trailing spaces and reduce multiple spaces to one
        query = query.trim().replaceAll("\\s+", " ");
        
        //check if the query contains a space, indicating it's a full name search
        if (query.contains(" ")) {
            
        	//split query into name and surname
            String[] parts = query.split(" ");
            
            //check if there are exactly two parts (name and surname)
            if (parts.length == 2) {
                String name = parts[0];
                String surname = parts[1];
                
                //search by Name + Surname and Surname + Name
                List<User> usersByNameSurname = userDAO.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname);
                List<User> usersBySurnameName = userDAO.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(surname, name);
                
                //combine and remove duplicates
                users.addAll(usersByNameSurname);
                users.addAll(usersBySurnameName);
                users = users.stream().distinct().collect(Collectors.toList());
            } else {
                
            	//if there are not exactly two parts (e.g., empty string or too many parts)
                users = handleSinglePartQuery(query);
            }
        } else {
            
        	//handle single part queries (name, surname, or email)
            users = handleSinglePartQuery(query);
        }
        
        return users;
    }

    //method for handling single-part queries (name, surname, or email)
    private List<User> handleSinglePartQuery(String query) {
        List<User> users = new ArrayList<>();
        List<User> usersByEmail = userDAO.findByEmailContainingIgnoreCase(query);
        List<User> usersByName = userDAO.findByNameContainingIgnoreCase(query);
        List<User> usersBySurname = userDAO.findBySurnameContainingIgnoreCase(query);

        //combine the results from all three searches and remove duplicates
        users.addAll(usersByEmail);
        users.addAll(usersByName);
        users.addAll(usersBySurname);
        return users.stream().distinct().collect(Collectors.toList());
    }
    
    //generic method for paginating any list
    public static <T> List<T> getPaginatedList(List<T> list, int page, int size) {

        if (size <= 0) {
            size = 10;
        }
        if (page <= 0) {
            page = 1; 
        }

        //handle empty list case
        if (list == null || list.isEmpty()) {
        	return null;
        }

        //calculate indices for pagination
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(list.size(), page * size);

        //return the sublist for pagination
        return list.subList(fromIndex, toIndex);
    }

    //generic method to get total pages
    public static <T> int getTotalPages(List<T> list, int resultsPerPage) {
        return (int) Math.ceil((double) list.size() / resultsPerPage);
    }

    //generic method to get the start page for pagination block
    public static int getStartPage(int currentPage, int blockSize) {
        return (currentPage - 1) / blockSize * blockSize + 1;
    }

    //generic method to get the end page for pagination block
    public static int getEndPage(int currentPage, int blockSize, int totalPages) {
        int endPage = (currentPage / blockSize) * blockSize + blockSize;
        return Math.min(endPage, totalPages);
    }
    
    // Method to sort forms by surname (A-Z or Z-A)
    public static List<Generalform> sortBySurname(List<Generalform> forms, boolean ascending) {
        return forms.stream()
                .sorted((form1, form2) -> ascending
                        ? form1.getSurname().compareToIgnoreCase(form2.getSurname())
                        : form2.getSurname().compareToIgnoreCase(form1.getSurname()))
                .collect(Collectors.toList());
    }

    public static List<Generalform> sortBySubmissionDate(List<Generalform> forms, boolean ascending) {
        return forms.stream()
                .sorted((form1, form2) -> ascending
                        ? form1.getSubmissionDate().compareTo(form2.getSubmissionDate())
                        : form2.getSubmissionDate().compareTo(form1.getSubmissionDate()))
                .collect(Collectors.toList());
    }

    // Generic method to sort by any property using a comparator
    public <T> List<T> sortList(List<T> list, Comparator<T> comparator, boolean ascending) {
        return list.stream()
                .sorted(ascending ? comparator : comparator.reversed())
                .collect(Collectors.toList());
    }
}
