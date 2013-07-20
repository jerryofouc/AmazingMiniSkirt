package com.netease.amazing.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.Notification;


public interface NotificationDao  extends PagingAndSortingRepository<Notification, Long>{
	/*@Query("from Notification n inner join Teacher t where n.id > ?1 and t.id=?2")
	Page<Notification> findNotificationLessThanID(long id, long teacherId, Sort sort, Pageable pageable);*/
	
	/*@Query("from Notification as n inner join n.teacher as t inner join t.klass as c  where c.id=?1")
	Page<Notification> findLatestNotification(long classId, Pageable pageable);*/
	
	@Query("select n from  com.netease.amazing.server.entity.Notification as n inner join n.parents as np inner join np.parent p where p.id=?1")
	List<Notification> findLatestNotification(long userId,Pageable pageable);

	@Query("select n from  com.netease.amazing.server.entity.Notification as n inner join n.parents as np inner join np.parent p where p.id=?1 and n.id < ?2")
	List<Notification> findRangeNotification(Long parentId, long beginId,Pageable pageable);
}
