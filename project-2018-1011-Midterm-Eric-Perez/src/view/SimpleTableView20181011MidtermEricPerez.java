package view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import model.Person;
import model.ModelProvider;



public class SimpleTableView20181011MidtermEricPerez {
   public final static String ID = "project-2018-1011-midterm-eric-perez.partdescriptor.simpletableview20180927midtermericperez";
   public final static String POPUPMENU = "project-2018-1011-midterm-eric-perez.popupmenu.actions_menu";

   private TableViewer viewer;
//   private ProgElemSorter progElemSorter;

   @Inject
   private ESelectionService selectionService;

   /**
    * Create contents of the view part.
    */
   @PostConstruct
   public void createControls(Composite parent, EMenuService menuService) {
      GridLayout layout = new GridLayout(2, false);
      parent.setLayout(layout);

      createViewer(parent);
      // register context menu on the table
      menuService.registerContextMenu(viewer.getControl(), POPUPMENU);
   }


   private void createViewer(Composite parent) {
      viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
      createColumnsProgElem(parent, viewer);
      final Table table = viewer.getTable();
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      viewer.setContentProvider(ArrayContentProvider.getInstance());
      viewer.setInput(ModelProvider.INSTANCE.getPersons());
      viewer.addSelectionChangedListener(new ISelectionChangedListener() {
         @Override
         public void selectionChanged(SelectionChangedEvent event) {
            IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
            Object firstElement = selection.getFirstElement();
            System.out.println("Do something with it: " + firstElement);

            // set the selection to the service
            selectionService.setSelection(selection.size() == 1 ? //
            firstElement : selection.toArray());
         }
      });

      GridData gridData = new GridData();
      gridData.verticalAlignment = GridData.FILL;
      gridData.horizontalSpan = 2;
      gridData.grabExcessHorizontalSpace = true;
      gridData.grabExcessVerticalSpace = true;
      gridData.horizontalAlignment = GridData.FILL;
      viewer.getControl().setLayoutData(gridData);


//      progElemSorter = new ProgElemSorter();
//      viewer.setComparator(progElemSorter);
   }

   public void setInput(Object data) {
      viewer.setInput(data);
   }

   private void createColumnsProgElem(final Composite parent, final TableViewer viewer) {
	   String[] titles = { "First name", "Last name", "Phone", "Address" };
	      int[] bounds = { 100, 100, 100, 100 };

	      TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	      col.setLabelProvider(new ColumnLabelProvider() {
	         @Override
	         public String getText(Object element) {
	            return ((Person) element).getFirstName();
	         }
	      });
	      col = createTableViewerColumn(titles[1], bounds[1], 1);
	      col.setLabelProvider(new ColumnLabelProvider() {
	         @Override
	         public String getText(Object element) {
	            return ((Person) element).getLastName();
	         }
	      });

	      col = createTableViewerColumn(titles[2], bounds[2], 2);
	      col.setLabelProvider(new ColumnLabelProvider() {
	         @Override
	         public String getText(Object element) {
	            return ((Person) element).getPhone();
	         }
	      });

	      col = createTableViewerColumn(titles[3], bounds[3], 3);
	      col.setLabelProvider(new ColumnLabelProvider() {
	         @Override
	         public String getText(Object element) {
	            return ((Person) element).getAddress();
	         }
	      });
   }

   private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
      final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
      final TableColumn column = viewerColumn.getColumn();
      column.setText(title);
      column.setWidth(bound);
      column.setResizable(true);
      column.setMoveable(true);
//      column.addSelectionListener(getSelectionAdapter(column, colNumber));
      return viewerColumn;
   }

//   private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
//      SelectionAdapter selectionAdapter = new SelectionAdapter() {
//         @Override
//         public void widgetSelected(SelectionEvent e) {
//            progElemSorter.setColumn(index);
//            int dir = progElemSorter.getDirection();
//            viewer.getTable().setSortDirection(dir);
//            viewer.getTable().setSortColumn(column);
//            viewer.refresh();
//         }
//      };
//      return selectionAdapter;
//   }

   public TableViewer getViewer() {
      return viewer;
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
   }

   public void refresh() {
      viewer.refresh();
   }
}
