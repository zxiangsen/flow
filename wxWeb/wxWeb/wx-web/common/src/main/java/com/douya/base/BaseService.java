package com.douya.base;

import com.douya.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public abstract class BaseService<D extends BaseDao<T>, T extends BaseEntity> {
	//required = false找不到匹配 Bean 时也不报错
	@Autowired(required = false)
	protected D dao;

	public BaseService() {
	}

	public T get(Long id) {
		return this.dao.get(id);
	}

	public T get(T entity) {
		return this.dao.get(entity);
	}

	public List<T> findList(T entity) {
		return this.dao.findList(entity);
	}

	public List<T> findAllList() {
		return this.dao.findAllList();
	}

	public PageInfo<T> findPage(PageInfo<T> page, T entity) {
		if (page.getPageNum() == 0) {
			page.setPageNum(1);
		}

		if (page.getPageSize() == 0) {
			page.setPageSize(10);
		}

		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("createDate desc");
		}

		PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0 ? page.getPageSize() : 10, page.getOrderBy());
		return new PageInfo(this.dao.findList(entity));
	}

	@Transactional(
			readOnly = false
	)
	public void save(T entity) {
		boolean isNew = true;
		if (entity.getId() != null) {
			BaseEntity oldEntity = this.get(entity.getId());
			isNew = oldEntity == null || oldEntity.getId() == null;
		}

		if (isNew) {
			this.insert(entity);
		} else {
			this.update(entity);
		}

	}

	@Transactional(
			readOnly = false
	)
	public void insert(T entity) {
		entity.preInsert();
		this.dao.insert(entity);
	}

	@Transactional(
			readOnly = false
	)
	public void update(T entity) {
		entity.preUpdate();
		this.dao.update(entity);
	}

	@Transactional(
			readOnly = false
	)
	public void dynamicUpdate(T entity) {
		entity.preUpdate();
		this.dao.dynamicUpdate(entity);
	}

	@Transactional(
			readOnly = false
	)
	public void delete(Long id) {
		this.dao.delete(id);
	}

	@Transactional(
			readOnly = false
	)
	public void delete(T entity) {
		this.dao.delete(entity);
	}

}
