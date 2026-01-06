package com.example.Varsani.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Driver.ArrivedOrders;
import com.example.Varsani.Staff.Driver.AssignedOrders;
import com.example.Varsani.Staff.Driver.DeliveredOrders;
import com.example.Varsani.Staff.Finance.ApprovedOrders;
import com.example.Varsani.Staff.Finance.ApprovedServPayments;
import com.example.Varsani.Staff.Finance.NewOrders;
import com.example.Varsani.Staff.Finance.NewServicePayments;
import com.example.Varsani.Staff.Finance.SupplyPayments;
import com.example.Varsani.Staff.ProductionTech.AssignedProduction;
import com.example.Varsani.Staff.ServMrg.CompletedHistory;
import com.example.Varsani.Staff.ServMrg.QuotationRequests;
import com.example.Varsani.Staff.ServMrg.ToGraduate;
import com.example.Varsani.Staff.ShippingMrg.OrdersToShip;
import com.example.Varsani.Staff.ShippingMrg.ShippingOrders;
import com.example.Varsani.Staff.Store_mrg.AssignProduction;
import com.example.Varsani.Staff.Store_mrg.CompletedProduction;
import com.example.Varsani.Staff.Store_mrg.RequestItems;
import com.example.Varsani.Staff.Store_mrg.RequestedMaterials;
import com.example.Varsani.Staff.Store_mrg.ViewProducts;
import com.example.Varsani.Staff.Store_mrg.ViewStock;
import com.example.Varsani.Staff.Supervisor.PendingProduction;
import com.example.Varsani.Staff.Supervisor.TrackProduction;
import com.example.Varsani.Staff.Technician.AssignedServices;
import com.example.Varsani.Staff.Technician.AssignedVisits;
import com.example.Varsani.Staff.Trainer.AssignedTrainees;
import com.example.Varsani.Staff.Trainer.Assignments;
import com.example.Varsani.Staff.Trainer.Attendance;
import com.example.Varsani.Staff.Trainer.Grades;
import com.example.Varsani.Staff.Trainer.SetExam;
import com.example.Varsani.Staff.Trainer.Units;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View contextView;
    private SessionHandler session;
    private UserModel user;

    private int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        setSupportActionBar(toolbar);

        contextView = findViewById(android.R.id.content);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            } else if (id == R.id.nav_new_orders) {
                startActivity(new Intent(getApplicationContext(), NewOrders.class));
            } else if (id == R.id.nav_approvedOrders) {
                startActivity(new Intent(getApplicationContext(), ApprovedOrders.class));
            } else if (id == R.id.nav_new_serv_payments) {
                startActivity(new Intent(getApplicationContext(), NewServicePayments.class));
            } else if (id == R.id.nav_approved_serv_payments) {
                startActivity(new Intent(getApplicationContext(), ApprovedServPayments.class));
            } else if (id == R.id.nav_supplier_payments) {
                startActivity(new Intent(getApplicationContext(), SupplyPayments.class));
            } else if (id == R.id.nav_orders_to_shipp) {
                startActivity(new Intent(getApplicationContext(), OrdersToShip.class));
            } else if (id == R.id.nav_shipping_orders) {
                startActivity(new Intent(getApplicationContext(), ShippingOrders.class));
            } else if (id == R.id.nav_assigned_orders) {
                startActivity(new Intent(getApplicationContext(), AssignedOrders.class));
            } else if (id == R.id.nav_arrived_orders) {
                startActivity(new Intent(getApplicationContext(), ArrivedOrders.class));
            } else if (id == R.id.nav_delivered_orders) {
                startActivity(new Intent(getApplicationContext(), DeliveredOrders.class));
            } else if (id == R.id.nav_stock) {
                startActivity(new Intent(getApplicationContext(), ViewStock.class));
            } else if (id == R.id.nav_products) {
                startActivity(new Intent(getApplicationContext(), ViewProducts.class));
            } else if (id == R.id.nav_quot_requests) {
                startActivity(new Intent(getApplicationContext(), QuotationRequests.class));
            } else if (id == R.id.nav_quot_visit) {
                startActivity(new Intent(getApplicationContext(), AssignedVisits.class));
            } else if (id == R.id.nav_assigned_services) {
                startActivity(new Intent(getApplicationContext(), AssignedServices.class));
            } else if (id == R.id.nav_materials) {
                startActivity(new Intent(getApplicationContext(), RequestedMaterials.class));
            } else if (id == R.id.nav_staff_feedback) {
                startActivity(new Intent(getApplicationContext(), Stafffeedback.class));
            } else if (id == R.id.nav_supplies) {
                startActivity(new Intent(getApplicationContext(), RequestItems.class));
            } else if (id == R.id.nav_assign_production) {
                startActivity(new Intent(getApplicationContext(), AssignProduction.class));
            } else if (id == R.id.nav_pending_production) {
                startActivity(new Intent(getApplicationContext(), PendingProduction.class));
            } else if (id == R.id.nav_track_production) {
                startActivity(new Intent(getApplicationContext(), TrackProduction.class));
            } else if (id == R.id.nav_assigned_production) {
                startActivity(new Intent(getApplicationContext(), AssignedProduction.class));
            } else if (id == R.id.nav_completed_production) {
                startActivity(new Intent(getApplicationContext(), CompletedProduction.class));
            } else if (id == R.id.nav_assigned_trainees) {
                startActivity(new Intent(getApplicationContext(), AssignedTrainees.class));
            } else if (id == R.id.nav_e_learning) {
                startActivity(new Intent(getApplicationContext(), Units.class));
            } else if (id == R.id.nav_assignments) {
                startActivity(new Intent(getApplicationContext(), Assignments.class));
            } else if (id == R.id.nav_exams) {
                startActivity(new Intent(getApplicationContext(), SetExam.class));
            } else if (id == R.id.nav_grades) {
                startActivity(new Intent(getApplicationContext(), Grades.class));
            } else if (id == R.id.nav_class_attendance) {
                startActivity(new Intent(getApplicationContext(), Attendance.class));
            } else if (id == R.id.nav_to_graduate) {
                startActivity(new Intent(getApplicationContext(), ToGraduate.class));
            } else if (id == R.id.nav_completed_trainees) {
                startActivity(new Intent(getApplicationContext(), CompletedHistory.class));
            } else if (id == R.id.nav_logout) {
                alertLogout();
            }

            drawer.closeDrawer(GravityCompat.START, true);
            return false;
        });
        drawer.closeDrawers();
        check();
    }

    public void alertLogout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setButton("Yes logout", (dialog, which) -> {
            session.logoutUser();
            Toast toast = Toast.makeText(getApplicationContext(), "You are logged out", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        alertDialog.setButton2("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            oNBackPressedExitApp();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    @Override
    protected void onStart() {
        super.onStart();
        k = 0;
    }

    public void oNBackPressedExitApp() {
        k++;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit App");
        alertDialog.setIcon(R.drawable.ic_notifications);
        alertDialog.setMessage("Do you really want to exit?");
        alertDialog.setCancelable(false);

        if (k == 1) {
            alertDialog.setButton("Yes", (dialog, which) -> {
                finish();
                Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
                homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
                homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeScreenIntent);
            });
            alertDialog.setButton2("No", (dialog, which) -> {
                dialog.cancel();
                k = 0;
            });
            alertDialog.show();
        }
    }

    public void check() {
        // hide all menu items initially
        int[] allMenuItems = new int[]{
                R.id.nav_new_orders, R.id.nav_approvedOrders, R.id.nav_new_serv_payments, R.id.nav_approved_serv_payments,
                R.id.nav_orders_to_shipp, R.id.nav_shipping_orders, R.id.nav_assigned_orders, R.id.nav_arrived_orders,
                R.id.nav_delivered_orders, R.id.nav_stock, R.id.nav_supplies, R.id.nav_quot_requests, R.id.nav_quot_visit,
                R.id.nav_assigned_services, R.id.nav_materials, R.id.nav_assign_production, R.id.nav_pending_production,
                R.id.nav_assigned_production, R.id.nav_track_production, R.id.nav_completed_production,
                R.id.nav_assigned_trainees, R.id.nav_e_learning, R.id.nav_assignments, R.id.nav_exams, R.id.nav_grades,
                R.id.nav_class_attendance, R.id.nav_to_graduate, R.id.nav_completed_trainees, R.id.nav_products
        };
        for (int id : allMenuItems) navigationView.getMenu().findItem(id).setVisible(false);

        // Staff feedback visible by default
        navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);

        if (session.isLoggedIn()) {
            switch (user.getUser_type()) {
                case "Finance":
                    setMenuVisible(new int[]{R.id.nav_new_orders, R.id.nav_approvedOrders, R.id.nav_new_serv_payments,
                            R.id.nav_approved_serv_payments, R.id.nav_supplier_payments});
                    break;
                case "Shipping Manager":
                    setMenuVisible(new int[]{R.id.nav_orders_to_shipp, R.id.nav_shipping_orders});
                    break;
                case "Driver":
                    setMenuVisible(new int[]{R.id.nav_assigned_orders, R.id.nav_arrived_orders, R.id.nav_delivered_orders});
                    break;
                case "Stock manager":
                    setMenuVisible(new int[]{R.id.nav_stock, R.id.nav_supplies, R.id.nav_assign_production,
                            R.id.nav_completed_production, R.id.nav_products});
                    break;
                case "Service manager":
                    setMenuVisible(new int[]{R.id.nav_quot_requests, R.id.nav_to_graduate, R.id.nav_completed_trainees});
                    break;
                case "Technician":
                    setMenuVisible(new int[]{R.id.nav_quot_visit, R.id.nav_assigned_services});
                    break;
                case "Supervisor":
                    setMenuVisible(new int[]{R.id.nav_pending_production, R.id.nav_track_production});
                    break;
                case "Production Technician":
                    setMenuVisible(new int[]{R.id.nav_assigned_production});
                    break;
                case "Trainer":
                    setMenuVisible(new int[]{R.id.nav_assigned_trainees, R.id.nav_e_learning, R.id.nav_assignments,
                            R.id.nav_exams, R.id.nav_grades, R.id.nav_class_attendance});
                    break;
            }
        }
    }

    private void setMenuVisible(int[] ids) {
        for (int id : ids) navigationView.getMenu().findItem(id).setVisible(true);
    }
}
