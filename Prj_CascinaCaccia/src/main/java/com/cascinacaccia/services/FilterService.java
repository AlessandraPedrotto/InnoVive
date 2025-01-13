package com.cascinacaccia.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.Informationform;
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
	
	/*
     * Method to retrieve all users from the database.
     *
     * @return A list of all users in the database
     */
    public List<User> getAllUsers() {
        return userDAO.findAll();  
    }
	
    /*
     * Method to sort a list of users by surname in ascending or descending order.
     *
     * @param users The list of users to sort
     * @param ascending A boolean flag indicating whether the sort should be in ascending order (true) or descending order (false)
     * @return A sorted list of users based on surname
     */
    public static List<User> sortUsersBySurname(List<User> users, boolean ascending) {
        return users.stream()
                .sorted((user1, user2) -> ascending 
                    ? user1.getSurname().compareToIgnoreCase(user2.getSurname()) 
                    : user2.getSurname().compareToIgnoreCase(user1.getSurname()))
                .collect(Collectors.toList());
    }
    
    /*
     * Method to search for users by email, name, surname, or full name.
     * If the query contains a space, it's treated as a full name search.
     *
     * @param query The search query, which could be a name, surname, or email, or a full name
     * @return A list of users that match the search query
     */
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

    /*
     * Helper method to handle single-part queries such as name, surname, or email.
     *
     * @param query The single-part query (name, surname, or email)
     * @return A list of users that match the single-part query
     */
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
    
    /*
     * Method to paginate a list of any type, returning a subset of the list for the current page.
     *
     * @param <T> The type of elements in the list
     * @param list The full list to paginate
     * @param page The current page number
     * @param size The number of results per page
     * @return A paginated sublist of the input list
     */
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

    /*
     * Method to calculate the total number of pages required for pagination.
     *
     * @param <T> The type of elements in the list
     * @param list The full list to paginate
     * @param resultsPerPage The number of results per page
     * @return The total number of pages required to display the list
     */
    public static <T> int getTotalPages(List<T> list, int resultsPerPage) {
        return (int) Math.ceil((double) list.size() / resultsPerPage);
    }

    /*
     * Method to calculate the start page number for a pagination block.
     *
     * @param currentPage The current page number
     * @param blockSize The size of each pagination block (e.g., 10 pages per block)
     * @return The start page number for the current pagination block
     */
    public static int getStartPage(int currentPage, int blockSize) {
        return (currentPage - 1) / blockSize * blockSize + 1;
    }

    /*
     * Method to calculate the end page number for a pagination block.
     *
     * @param currentPage The current page number
     * @param blockSize The size of each pagination block (e.g., 10 pages per block)
     * @param totalPages The total number of pages available
     * @return The end page number for the current pagination block
     */
    public static int getEndPage(int currentPage, int blockSize, int totalPages) {
        int endPage = (currentPage / blockSize) * blockSize + blockSize;
        return Math.min(endPage, totalPages);
    }
    
    /*
     * Method to sort forms by surname (A-Z or Z-A).
     *
     * @param forms The list of Generalform objects to sort
     * @param ascending A boolean flag indicating whether the sort should be in ascending order (true) or descending order (false)
     * @return A sorted list of Generalform objects based on surname
     */
    public static List<Generalform> sortBySurname(List<Generalform> forms, boolean ascending) {
        return forms.stream()
                .sorted((form1, form2) -> ascending
                        ? form1.getSurname().compareToIgnoreCase(form2.getSurname())
                        : form2.getSurname().compareToIgnoreCase(form1.getSurname()))
                .collect(Collectors.toList());
    }
    
    /*
     * Method to sort forms by submission date (ascending or descending).
     *
     * @param forms The list of Generalform objects to sort
     * @param ascending A boolean flag indicating whether the sort should be in ascending order (true) or descending order (false)
     * @return A sorted list of Generalform objects based on submission date
     */
    public static List<Generalform> sortBySubmissionDate(List<Generalform> forms, boolean ascending) {
        return forms.stream()
                .sorted((form1, form2) -> ascending
                        ? form1.getSubmissionDate().compareTo(form2.getSubmissionDate())
                        : form2.getSubmissionDate().compareTo(form1.getSubmissionDate()))
                .collect(Collectors.toList());
    }

    /*
     * Generic method to sort a list of any type using a comparator and sorting order.
     *
     * @param <T> The type of elements in the list
     * @param list The list of elements to sort
     * @param comparator The comparator to use for sorting
     * @param ascending A boolean flag indicating whether the sort should be in ascending order (true) or descending order (false)
     * @return A sorted list of elements
     */
    public <T> List<T> sortList(List<T> list, Comparator<T> comparator, boolean ascending) {
        return list.stream()
                .sorted(ascending ? comparator : comparator.reversed())
                .collect(Collectors.toList());
    }
    
    /*
     * Method to filter a list of forms based on category IDs.
     * Only forms with a category ID present in the provided list will be included.
     *
     * @param generalForms The list of Generalform objects to filter
     * @param categoryIds A list of category IDs to filter by
     * @return A filtered list of Generalform objects that belong to the specified categories
     */
    public static List<Generalform> filterFormsByCategories(List<Generalform> generalForms, List<String> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return generalForms;
        }

        return generalForms.stream()
                .filter(form -> categoryIds.contains(form.getCategory().getId())) // Assuming each Generalform has a 'category' field with 'getId()' returning a String
                .collect(Collectors.toList());
    }
    
    /*
     * Method to filter forms by their associated Informationform or Bookingform statuses.
     * The status list is matched against the statuses of related Informationforms or Bookingforms.
     *
     * @param generalForms The list of Generalform objects to filter
     * @param statuses A list of statuses to filter by
     * @return A filtered list of Generalform objects that match the given statuses
     */
    public static List<Generalform> filterFormsByStatuses(List<Generalform> generalForms, List<String> statuses) {
        if (statuses == null || statuses.isEmpty()) {
            return generalForms; // Return all if no statuses are selected
        }

        System.out.println("Filtering Generalforms with statuses: " + statuses);

        return generalForms.stream()
                .filter(form -> {
                	
                	//check related Informationforms and Bookingforms statuses
                    List<Informationform> informationForms = form.getInformationForms(); 
                    List<BookingForm> bookingForms = form.getBookingForms();
                    
                    //check if the statuses match in either the informationForms or bookingForms
                    boolean matchesInformationformStatus = false;
                    boolean matchesBookingformStatus = false;
                    
                    if (informationForms != null) {
                        
                    	//check the status of each Informationform in the list
                    	matchesInformationformStatus = informationForms.stream()
                                .anyMatch(informationform -> 
                                    statuses.stream().anyMatch(status -> status.equalsIgnoreCase(informationform.getStatus()))
                                );
                    }
                    
                    //check statuses in the Bookingforms list
                    if (bookingForms != null) {
                    	matchesBookingformStatus = bookingForms.stream()
                            .anyMatch(bookingform -> 
                                statuses.stream().anyMatch(status -> status.equalsIgnoreCase(bookingform.getStatus())
                                )
                            );
                    }
                    
                    return matchesInformationformStatus || matchesBookingformStatus;
                })
                .collect(Collectors.toList());
    }
    
    /*
     * Method to filter a list of Generalform objects to include only those that have associated Informationforms.
     *
     * @param generalForms The list of Generalform objects to filter
     * @return A filtered list of Generalform objects that have associated Informationforms
     */
    public static List<Generalform> filterByInformationForm(List<Generalform> generalForms) {
        return generalForms.stream()
                .filter(form -> form.getInformationForms() != null && !form.getInformationForms().isEmpty())  // Assuming the relation is set up
                .collect(Collectors.toList());
    }

    /*
     * Method to filter a list of Generalform objects to include only those that have associated Bookingforms.
     *
     * @param generalForms The list of Generalform objects to filter
     * @return A filtered list of Generalform objects that have associated Bookingforms
     */
    public static List<Generalform> filterByBookingForm(List<Generalform> generalForms) {
        return generalForms.stream()
                .filter(form -> form.getBookingForms() != null && !form.getBookingForms().isEmpty())  // Assuming the relation is set up
                .collect(Collectors.toList());
    }
    
    /*
     * Method to filter a list of Generalform objects based on whether they have any assigned Informationforms or Bookingforms.
     *
     * @param generalForms The list of Generalform objects to filter
     * @param isAssigned A boolean flag where:
     *                   true means filter for forms with at least one assigned Informationform or Bookingform,
     *                   false means filter for forms with no assigned Informationform or Bookingform
     * @return A filtered list of Generalform objects based on the assignment status of their nested forms
     */
    public static List<Generalform> filterByAssignment(List<Generalform> generalForms, boolean isAssigned) {
        return generalForms.stream()
                .filter(form -> {
                    boolean hasAssignedInformationform = form.getInformationForms() != null && 
                                                         form.getInformationForms().stream()
                                                             .anyMatch(informationForm -> informationForm.getAssignedUser() != null);
                    boolean hasAssignedBookingform = form.getBookingForms() != null && 
                                                     form.getBookingForms().stream()
                                                         .anyMatch(bookingForm -> bookingForm.getAssignedUser() != null);
                    
                    // If isAssigned is true, filter for forms with assigned Informationforms or Bookingforms
                    // If isAssigned is false, filter for forms with no assigned Informationforms or Bookingforms
                    return (isAssigned && (hasAssignedInformationform || hasAssignedBookingform)) ||
                           (!isAssigned && !hasAssignedInformationform && !hasAssignedBookingform);
                })
                .collect(Collectors.toList());
    }
}
