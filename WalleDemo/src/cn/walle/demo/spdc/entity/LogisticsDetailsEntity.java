package cn.walle.demo.spdc.entity;

import java.util.List;

import cn.walle.demo.spdc.model.LogisticsCargoModel;
import cn.walle.demo.spdc.model.LogisticsContainerModel;
import cn.walle.demo.spdc.model.LogisticsDetailsModel;

public class LogisticsDetailsEntity extends LogisticsDetailsModel {

	private List<LogisticsCargoModel> cargos;
	
	private List<LogisticsContainerModel> containers;

	public List<LogisticsCargoModel> getCargos() {
		return cargos;
	}

	public void setCargos(List<LogisticsCargoModel> cargos) {
		this.cargos = cargos;
	}

	public List<LogisticsContainerModel> getContainers() {
		return containers;
	}

	public void setContainers(List<LogisticsContainerModel> containers) {
		this.containers = containers;
	}
	
}
