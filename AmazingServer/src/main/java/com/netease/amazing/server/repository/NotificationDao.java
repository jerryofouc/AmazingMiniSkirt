package com.netease.amazing.server.repository;

import com.netease.amazing.server.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract interface NotificationDao extends PagingAndSortingRepository<Notification, Long>
{
  @Query("select n from  com.netease.amazing.server.entity.Notification as n inner join n.parents as np inner join np.parent p where p.id=?1")
  public abstract List<Notification> findLatestNotification(long paramLong, Pageable paramPageable);

  @Query("select n from  com.netease.amazing.server.entity.Notification as n inner join n.parents as np inner join np.parent p where p.id=?1 and ?2 >  n.id ")
  public abstract List<Notification> findRangeNotification(Long paramLong, long paramLong1, Pageable paramPageable);

  @Query("select n from  com.netease.amazing.server.entity.Notification as n inner join n.parents as np inner join np.parent p where p.id=?1 and ?2 < n.id  ")
  public abstract List<Notification> findRangeUpNotification(Long paramLong, long noticeId, Sort paramSort);

  @Modifying
  @Query("delete from ParentNotification pn where pn.parent.id=?1 and pn.notification.id=?2")
  public abstract void deletePNByUserIdAndNotificationId(Long paramLong1, Long paramLong2);

  @Modifying
  @Query("delete from TeacherTip tp where tp.teacher.user.id=?1 and tp.tip.id=?2")
  public abstract void deleteByTPUserIdAndTipId(Long paramLong1, Long paramLong2);
}