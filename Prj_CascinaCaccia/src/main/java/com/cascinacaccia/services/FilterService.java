package com.cascinacaccia.services;

import java.util.ArrayList;
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
	UserDAO userDAO;
	
	//method to get all users from the database
    public List<User> getAllUsers() {
        return userDAO.findAll();  
    }
	
    //method to sort users by surname in ascending or descending order
    public List<User> sortUsersBySurname(List<User> users, boolean ascending) {
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
    
    //method for the pagination
    public List<User> getPaginatedUsers(List<User> users, int page, int size) {
        
    	//ensure valid pagination parameters
        if (size <= 0) {
            //default to 10 items per page if the size is invalid
            size = 10;
        }
        if (page <= 0) {
            //default to the first page if the page number is invalid
            page = 1;
        }

        //handle empty list case
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        //calculate the indices for pagination
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(users.size(), page * size);

        //if fromIndex is out of bounds or toIndex < fromIndex, return an empty list
        if (fromIndex >= users.size() || fromIndex > toIndex) {
            return new ArrayList<>();
        }

        //return the sublist for pagination
        return users.subList(fromIndex, toIndex);
    }
    
    //method to get all the pages
    public int getTotalPages(List<User> allUsers, int resultsPerPage) {
        return (int) Math.ceil((double) allUsers.size() / resultsPerPage);
    }
    
    //method to get the first page
    public int getStartPage(int currentPage, int blockSize) {
        return (currentPage - 1) / blockSize * blockSize + 1;
    }
    
    //method to get the last page
    public int getEndPage(int currentPage, int blockSize, int totalPages) {
        int endPage = (currentPage / blockSize) * blockSize + blockSize;
        return Math.min(endPage, totalPages);
    }
    
    //method for the pagination (requests)
    public List<Generalform> getPaginatedRequest(List<Generalform> generalForms, int page, int size) {
        
    	//ensure valid pagination parameters
        if (size <= 0) {
            //default to 10 items per page if the size is invalid
            size = 10;
        }
        if (page <= 0) {
            //default to the first page if the page number is invalid
            page = 1;
        }

        //handle empty list case
        if (generalForms == null || generalForms.isEmpty()) {
            return new ArrayList<>();
        }

        //calculate the indices for pagination
        int fromIndex = Math.max(0, (page - 1) * size);
        int toIndex = Math.min(generalForms.size(), page * size);

        //if fromIndex is out of bounds or toIndex < fromIndex, return an empty list
        if (fromIndex >= generalForms.size() || fromIndex > toIndex) {
            return new ArrayList<>();
        }

        //return the sublist for pagination
        return generalForms.subList(fromIndex, toIndex);
    }
    
    //method to get all the pages (requests)
    public int getTotalPagesRequest(List<User> allUsers, int resultsPerPage) {
        return (int) Math.ceil((double) allUsers.size() / resultsPerPage);
    }
    
    //method to get the first page (requests)
    public int getStartPageRequest(int currentPage, int blockSize) {
        return (currentPage - 1) / blockSize * blockSize + 1;
    }
    
    //method to get the last page (requests)
    public int getEndPageRequest(int currentPage, int blockSize, int totalPages) {
        int endPage = (currentPage / blockSize) * blockSize + blockSize;
        return Math.min(endPage, totalPages);
    }
}
