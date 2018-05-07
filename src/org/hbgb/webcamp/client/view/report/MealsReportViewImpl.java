package org.hbgb.webcamp.client.view.report;

import java.util.ArrayList;

import org.hbgb.webcamp.client.presenter.IReportPresenter;
import org.hbgb.webcamp.client.widget.LoadingPopup;
import org.hbgb.webcamp.shared.Day;
import org.hbgb.webcamp.shared.Meal;
import org.hbgb.webcamp.shared.MealsReport;
import org.hbgb.webcamp.shared.Week;
import org.hbgb.webcamp.shared.enums.DietType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class MealsReportViewImpl extends Composite implements IMealsReportView
{
	@UiTemplate(value = "MealsReportView.ui.xml")
	static interface MealsReportViewImplBinder extends UiBinder<Widget, MealsReportViewImpl>
	{
	}

	private static UiBinder<Widget, MealsReportViewImpl> binder = GWT
			.create(MealsReportViewImplBinder.class);

	@UiField
	FlexTable table;

	LoadingPopup loadPop;

	IReportPresenter presenter;

	public MealsReportViewImpl()
	{
		initWidget(binder.createAndBindUi(this));
		table.setBorderWidth(1);
		table.setCellPadding(5);
		loadPop = new LoadingPopup();
	}

	@Override
	public void setPresenter(IReportPresenter p)
	{
		presenter = p;
	}

	@Override
	public void setReportData(MealsReport report)
	{
		table.removeAllRows();
		formatAWeek(report.getEarlyWeek());
		formatAWeek(report.getBurnWeek());
		formatErrors(report.getBadList());
		loadPop.stop();
	}

	private void formatAWeek(Week week)
	{
		FlexTable.FlexCellFormatter formatter = table.getFlexCellFormatter();
		int i = table.getRowCount();
		table.setWidget(i, 0, new HTML(week.getName()));
		formatter.setColSpan(i, 0, 9);
		table.setWidget(++i, 0, new HTML("Date"));
		table.setWidget(i, 1, new HTML("Meal"));
		table.setWidget(i, 2, new HTML("Omnivore"));
		table.setWidget(i, 3, new HTML("Vegetarian"));
		table.setWidget(i, 4, new HTML("Vegan  "));
		table.setWidget(i, 5, new HTML("Total  "));
		table.setWidget(i, 6, new HTML("Gluten Free"));
		table.setWidget(i, 7, new HTML("Restrictions"));
		for (Day day : week.getDays())
		{
			formatADay(day);
		}
	}

	private void formatADay(Day day)
	{
		FlexTable.FlexCellFormatter formatter = table.getFlexCellFormatter();
		int i = table.getRowCount();

		// put the meals down starting on this row
		// first one skips a cell where day will go
		formatAMeal(day.getBreakfast(), 1);
		formatAMeal(day.getLunch(), 0);
		formatAMeal(day.getDinner(), 0);

		// go back and put day in cell just left of breakfast and make it 3 rows
		// tall
		table.setWidget(i, 0, new HTML(day.getToday().toString()));
		formatter.setRowSpan(i, 0, 3);

		StringBuilder sb = new StringBuilder("");
		for (String rest : day.getRestrictions())
		{
			sb.append(rest);
		}

		// put restrictions after the 4 other columns and diet types
		table.setWidget(i, DietType.values().length + 4, new HTML(sb.toString()));
		formatter.setRowSpan(i, DietType.values().length + 4, 3);
	}

	private void formatAMeal(Meal meal, int j)
	{
		int i = table.getRowCount();
		Integer sum = 0;
		table.setWidget(i, j, new HTML(meal.getName()));
		int[] arrn = meal.getDietNumbers();
		int n = arrn.length;
		int n2 = 0;
		while (n2 < n)
		{
			Integer num = arrn[n2];
			sum = sum + num;
			table.setWidget(i, ++j, new HTML(num.toString()));
			++n2;
		}
		table.setWidget(i, ++j, new HTML(sum.toString()));
		Integer temp = meal.getGlutenFreeNum();
		table.setWidget(i, ++j, new HTML(temp.toString()));
	}

	private void formatErrors(ArrayList<String> badList)
	{
		int i = table.getRowCount();

		table.setWidget(i++, 0, new HTML("Campers with Missing Data"));

		for (String name : badList)
		{
			table.setWidget(i++, 0, new HTML(name));
		}
	}

	@Override
	public void clear()
	{
		loadPop.go();
	}

}
