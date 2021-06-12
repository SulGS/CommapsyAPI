package com.sulgames.commapsy.entities.PlaceRequest;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PlaceRequestDAO extends JpaRepository<PlaceRequest, Integer>{
	
	
	@Modifying
	@Query(value = "INSERT INTO PlaceRequest(User_Mail,SendDate,PlaceID,Latitude,Longitude,Name,Photo,Description,Category,IsAccepted) values(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,false)" , nativeQuery = true)
	@Transactional
	public void customSave(@Param("p1") String uM,@Param("p2") Date send,@Param("p3") int ID,@Param("p4") double lat,@Param("p5") double lon,
			@Param("p6")String n,@Param("p7")String photo, @Param("p8")String desc,@Param("p9") String category);

	
	@Modifying
    @Query("delete from PlaceRequest p where p.PlaceID = ?1")
    void deleteRequestsByID(int id);
	
	@Query(value = "SELECT p FROM PlaceRequest p WHERE p.Admin_Mail is null")
	public List<PlaceRequest> getNoReplyRequests(Pageable pageable);
	
}
