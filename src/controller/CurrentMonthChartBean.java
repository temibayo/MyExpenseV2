package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import model.CurrentMonthSummary;
import model.UserExpensePerCategory;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import utils.DateUtil;
import utils.Constants;

import dataAccessor.UserExpenseRecordAccessor;

//This class is used to populate the All Expense Bar Chart on the loggedin Homepage
@ManagedBean
public class CurrentMonthChartBean {
	private List<UserExpensePerCategory> userExpense;
    private CartesianChartModel categoryModel;
   
    
    @ManagedProperty(value="#{userProfileBean}")
	private UserProfileBean upBean;
    
    @PostConstruct
    public void init(){
		userExpense = new ArrayList<UserExpensePerCategory>();
		DateUtil date = new DateUtil();
    	UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
    	uerAccessor.getCurrentMonthAllExpense(userExpense, date.getCurrentMonth(), date.getCurrentYear(),
				upBean.getUserID());
        createCategoryModel();
        
	}

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    private void createCategoryModel() {
        categoryModel = new CartesianChartModel();
        Constants constants = new Constants();
        ChartSeries expenseCategory = new ChartSeries();
        expenseCategory.setLabel("All Expense For Month");
        
        for(UserExpensePerCategory uepc : userExpense){
        	expenseCategory.set(constants.getCategory(uepc.getCategoryID()), Double.parseDouble(uepc.getExpense()));
        }
        
        categoryModel.addSeries(expenseCategory);
    }

	public void setUpBean(UserProfileBean upBean) {
		this.upBean = upBean;
	}
}
