package com.netease.amazing.server.repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.netease.amazing.server.entity.Tip;
public interface TipDao extends PagingAndSortingRepository<Tip, Long>{
}
