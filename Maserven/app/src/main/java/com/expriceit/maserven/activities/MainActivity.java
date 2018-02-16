package com.expriceit.maserven.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expriceit.maserven.MaservenApplication;
import com.expriceit.maserven.R;
import com.expriceit.maserven.adaptadores.Adapter;
import com.expriceit.maserven.adaptadores.AdapterListMiDetPedido;
import com.expriceit.maserven.adaptadores.AdapterListMisPedidos;
import com.expriceit.maserven.adaptadores.AdapterListPedidos;
import com.expriceit.maserven.adaptadores.AdapterMisClientes;
import com.expriceit.maserven.adaptadores.MItems;
import com.expriceit.maserven.adaptadores.SpinnerAdapter;
import com.expriceit.maserven.entities.AllConsultaClientesWS;
import com.expriceit.maserven.entities.ConsultaCliente;
import com.expriceit.maserven.entities.ItemListPedidos;
import com.expriceit.maserven.entities.Items;
import com.expriceit.maserven.entities.PersonaModel;
import com.expriceit.maserven.entities.SyncPedidosWS;
import com.expriceit.maserven.mismodelos.Clientes;
import com.expriceit.maserven.mismodelos.DetallePedidos;
import com.expriceit.maserven.mismodelos.Jobs;
import com.expriceit.maserven.mismodelos.Pedidos;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout Fragment_cli,FrameConsultaCliente,FrameRegistraCliente,FrameNewPedido,Frame_lista_pedidos,FramePedidosDiariosOk;
   // LayoutInflater Layout_ConsultarCliente,Layout_RegistraCliente;
    TextView IdPedido, TextMenu_ConsultarCliente, TextMenu_RegistrarCliente, TextCodigoCliente, TextNombreCliente, TextNombreUser,Tv_Descripcion,Tv_Cod_Interno,Tv_Cod_alterno,Tv_PVP,Tv_Stock,dlgCodCliente,dlgRazonSocial,np_identificacion,np_cliente;
    TextView tv_ma_subtotal,tv_ma_iva,tv_ma_total;
    EditText edt_ma_desc;
    CheckBox ChkAgregarClinte;
    EditText Edit_in_identificaion, dato_consultar,ma_Codigo,ma_identificacion,ma_cliente,rc_identificacion,rc_nombre_cliente;
    Button btn_consulta_cliente, btn_registra_cliente;
    FloatingActionButton Btn_NuevoPedido;
    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_USER = "usuario";
    String KEY_PIN = "pin_acceso";
    String KEY_PASS="pass";
    String DESCRIPCION_PROD = "descripcion";
    ImageButton img_consul_cli, img_reg_cliente,img_add_items;
    ImageButton btn_sync, btn_update_suma;
    Boolean lb_newPedido=false;
    Spinner opciones;
    public ListView ListProductos,miListadePedidos,mis_pedidos_diarios_listv,ListaMisClientes;
    private Adapter adapter;
    private AdapterMisClientes adapter_mis_clientes;
    private AdapterListPedidos adapterListPedido;
    public AdapterListMisPedidos adapter_mis_pedidos;
    public AdapterListMiDetPedido adapter_list_det_pedido;
    private AlertDialog dialogo_cancel;
    private List<String> lstSource = new ArrayList<>();
    private int TipoConsulta=0;
    Button btnConsultaItem,btnConsultaClienteOK;

    EditText dlg_cantidad;
    private ArrayList<ItemListPedidos.getItems> model2;

    private ListView lvProductos;
    private Call<Items.DatosItem> CallItems;
    private Call<AllConsultaClientesWS.DatosCliente> CallClientes;
    private Call<SyncPedidosWS.DatosPedidos> callInsertPedidos;
    private Call<SyncPedidosWS.DatosDetPedidos> callInsertDetPedidos;
    private Call<SyncPedidosWS.DatosEnviados> callPedidoEnviado;
    private ItemListPedidos.getItems m;
    private Call<ConsultaCliente.getCliente> CallConsulCliente;
    //Modelos
    private Jobs jobs;
    private Clientes clientes;
    private DetallePedidos detpedidos;
    private Pedidos pedidos;
    public List<Pedidos> pedidos_List;
    public ArrayList<Pedidos> pedidos_arrayList;
    private DetallePedidos detallepedido;
    public List<DetallePedidos> list_detalles_pedidos;
    public ArrayList<DetallePedidos> arrayList_detalles_pedidos;
    ProgressBar progresConsulItem,progressBarListClientes, progres_sync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         Fragment_cli = (FrameLayout) findViewById(R.id.fragmentcli);
        FramePedidosDiariosOk = (FrameLayout) findViewById(R.id.MisPedidosDiarios);
         Fragment_cli.setVisibility(View.INVISIBLE);
        Frame_lista_pedidos = (FrameLayout) findViewById(R.id.MisPedidosDiarios);
        Frame_lista_pedidos.setVisibility(View.VISIBLE);
        TextMenu_ConsultarCliente = (TextView) findViewById(R.id.textMenuConsultarCliente);
        TextMenu_RegistrarCliente = (TextView) findViewById(R.id.textMenuRegistrarCliente);

        Tv_Descripcion = (TextView) findViewById(R.id.tv_descripcion);
        Tv_Cod_Interno = (TextView) findViewById(R.id.tv_codigo_interno);
        Tv_Cod_alterno = (TextView) findViewById(R.id.tv_codigo_alterno);
        Tv_PVP = (TextView) findViewById(R.id.tv_pvp);
        Tv_Stock = (TextView) findViewById(R.id.tv_stock);
        tv_ma_subtotal = (TextView)findViewById(R.id.ma_tv_subtotal);
        tv_ma_total = (TextView)findViewById(R.id.ma_tv_total);
        tv_ma_iva = (TextView)findViewById(R.id.ma_tv_iva);
        edt_ma_desc = (EditText)findViewById(R.id.ma_et_descuento);
//tv_ma_subtotal tv_ma_total  tv_ma_iva edt_ma_desc np_cliente

        Edit_in_identificaion = (EditText) findViewById(R.id.edit_in_identificacion);
        TextCodigoCliente = (TextView) findViewById(R.id.textCodCliente);
        TextNombreUser = (TextView) findViewById(R.id.textNombreUsuario);
        np_identificacion =(TextView)findViewById(R.id.tv_TMIdentificacion);
        np_cliente = (TextView) findViewById(R.id.tv_TMCliente);
        TextNombreCliente = (TextView) findViewById(R.id.textNombreCliente);
        IdPedido = (TextView) findViewById(R.id.textID_Pedido);
        ChkAgregarClinte = (CheckBox) findViewById(R.id.checkBoxAgregarCliente);
        mis_pedidos_diarios_listv = (ListView) findViewById(R.id.list_mis_pedidos_diarios);
        btn_update_suma = (ImageButton) findViewById(R.id.img_btn_update_sum);



        FrameConsultaCliente =(FrameLayout) findViewById(R.id.frameConsultaCliente);
        FrameRegistraCliente =(FrameLayout) findViewById(R.id.frameRegistraCliente);
        FrameNewPedido = (FrameLayout) findViewById(R.id.IdNuevoPedido);
        //btn_registra_cliente = (Button) findViewById(R.id.btnRegistrarCliente) ;
        btn_consulta_cliente = (Button) findViewById(R.id.btnConsultaCliente) ;
        TextNombreUser.setText(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER));
        TextCodigoCliente.setVisibility(View.GONE);
        TextNombreCliente.setVisibility(View.GONE);
        ChkAgregarClinte.setVisibility(View.GONE);
        FrameConsultaCliente.setVisibility(View.GONE);
        FrameRegistraCliente.setVisibility(View.GONE);
        FrameNewPedido.setVisibility(View.GONE);

        img_consul_cli = (ImageButton) findViewById(R.id.imgBtnBuscarCliente);
        img_reg_cliente= (ImageButton) findViewById(R.id.imgBtnAddCliente);
        img_add_items =(ImageButton) findViewById(R.id.imgBtnBuscarItem);
        ma_Codigo = (EditText)findViewById(R.id.editTMCodigo);
        ma_cliente = (EditText)findViewById(R.id.editTMCliente);
        ma_identificacion = (EditText) findViewById(R.id.editTMIdentificacion);



        mis_pedidos_diarios_listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                try{

                    final Pedidos itemPedido = (Pedidos) adapter_mis_pedidos.getItem(i);

                    Log.w("adapter_mis_pedidos", "Esta Seleccionando el siguiente Item:"+itemPedido.getId()+" - "+itemPedido.getNom_cliente());
                 //   consultar_mi_pedido_db(Integer.parseInt(itemPedido.getId().toString()));
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.dialog_menu_list_pedidos,null);
            ImageButton btn_Editar = (ImageButton) mView.findViewById(R.id.img_btn_editar);
                     btn_sync = (ImageButton) mView.findViewById(R.id.img_btn_sincronizar);
                    progres_sync = (ProgressBar) mView.findViewById(R.id.progressBar11);
                    ImageButton btn_eliminar = (ImageButton) mView.findViewById(R.id.img_btn_eliminar);

                    mBuilder.setView(mView);
                    final AlertDialog dialogMenu = mBuilder.create();
                    dialogMenu.show();

                    btn_eliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogMenu.dismiss();
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                            dialogo1.setTitle("Mensaje!!!");
                            dialogo1.setMessage("¿Esta seguro que desea Eliminar este Pedido?");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("Eliminar el pedido n°", "IDPedidoInt:"+itemPedido.getId().toString());
                                    eliminarPedido(Integer.parseInt(itemPedido.getId().toString()));
                                    consulta_mis_pedidos();



                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("mensaje", "No pasa nada");

                                }
                            });
                            dialogo1.show();



                        }
                    });
                    btn_Editar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            consultar_mi_pedido_db(Integer.parseInt(itemPedido.getId().toString()));

                            dialogMenu.dismiss();
                        }
                    });
                    btn_sync.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progres_sync.setVisibility(View.VISIBLE);
                            btn_sync.setVisibility(View.GONE);

                            pedidos = new Pedidos();
                            pedidos = Pedidos.findById(Pedidos.class,Integer.parseInt(itemPedido.getId().toString()));
                            pedidos.getNum_pedido();
                            if(pedidos.getIdent_cliente().equals("0000000000000")){
                                Utils.generarAlerta(MainActivity.this, "ALERTA!", "Favor ingrese el Cliente que realiza el Pedido");

                                progres_sync.setVisibility(View.GONE);
                                btn_sync.setVisibility(View.VISIBLE);
                                return;

                            }


                            if(pedidos.getNum_pedido().equals("0")){


                                enviarPedidoID(Integer.parseInt(itemPedido.getId().toString()));

                            }else{
                                Toast.makeText(MainActivity.this, "El Pedido esta Sincronizado...", Toast.LENGTH_SHORT).show();
                                //progres_sync.setVisibility(View.INVISIBLE);
                                //btn_sync.setEnabled(true);
                                progres_sync.setVisibility(View.GONE);
                                btn_sync.setVisibility(View.VISIBLE);
                            }


                        }
                    });





                }catch (Exception e){
                    e.getMessage();
                    e.getStackTrace();
                    Log.w("Error", "error al Intentar eliminar el item:"+e.getMessage()+" e.getStackTrace() "+e.getStackTrace());

                }



            }
        });




        //TODO: funcion que consulta pedidos diarios
        consulta_mis_pedidos();
        //       miListadePedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {


        btn_update_suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SumaTotales();
            }
        });

       /************
        try{
            jobs = new Jobs();
            jobs.setJobTitle("Stalisto");
            jobs.save();

            clientes = new Clientes("243","34555975123401","NIVEN DIOS S.A.");
            clientes.save();
            clientes = Clientes.findById(Clientes.class, 1); // 1 is the record's id.
            Log.e("Mi Cliente", clientes.getId() +
                    ", " + clientes.getCodigo() +
                    ", " + clientes.getIdetificacion() +
                    ", " + clientes.getNombre_cliente()
            );

            List<Jobs> jobslist = Jobs.listAll(Jobs.class);
            jobslist.get(0).getJobTitle();

            for( Jobs j : jobslist){
                //imprimimos el objeto pivote
                Log.w("Item 0"," jobslist.get(0).getJobTitle()-->>"+j.getJobTitle());

            }


            Toast.makeText(MainActivity.this, "Ingresado Correctamente...", Toast.LENGTH_SHORT).show();
        }*/
/*
        adapter = new Adapter(getBaseContext(), otp.getData());
        ListProductos.setAdapter(adapter);*/

        /*********/

        //spinner
        lstSource.add("Seleccionar tipo Consulta");
        lstSource.add("Código Interno");
        lstSource.add("Código Alterno");
        lstSource.add("Descripción");
        lstSource.add("Linea");
        IdPedido.setText("");



       /* ArrayList<MItems> model = new ArrayList<MItems>();
        MItems m = new MItems();

        m.setCodigo_interno("001");
        m.setCodigo_alterno("AFG092");
        m.setDescripcion("MARTILLO");
        m.setPvp("10.00");
        m.setStock("2000");

        model.add(m);
        //-------------
        m = new MItems();
        m.setCodigo_interno("003");
        m.setCodigo_alterno("AFQW92");
        m.setDescripcion("RODILLO");
        m.setPvp("15.00");
        m.setStock("2100");
        model.add(m);

        //-------------
        m = new MItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("CLAVO 2.5\" ");
        m.setPvp("15.00");
        m.setStock("2100");

        model.add(m);


        adapter = new Adapter(this, model);
        ListProductos = (ListView) findViewById(R.id.ma_lv_lista_productos);
        ListProductos.setAdapter(adapter);*/

    /*    ArrayList<ItemListPedidos.getItems> model2 = new ArrayList<ItemListPedidos.getItems>();
        ItemListPedidos.getItems m = new ItemListPedidos.getItems();

        m.setCodigo_interno("001");
        m.setCodigo_alterno("AFG092");
        m.setDescripcion("MARTILLO");
        m.setPvp("10.00");
        m.setCantidad("20");
        m.setTotal("2");
        //  m.setStock("2000");

        model2.add(m);
        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("003");
        m.setCodigo_alterno("AFQW92");
        m.setDescripcion("RODILLO");
        m.setPvp("15.00");
        m.setCantidad("40");
        m.setTotal("5");
        model2.add(m);

        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("CLAVO 2.5\" ");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("3");

        model2.add(m);


        adapterListPedido = new AdapterListPedidos(this, model2);
        miListadePedidos = (ListView) findViewById(R.id.MiListadePedidos);
        miListadePedidos.setAdapter(adapterListPedido);*/
        model2 = new ArrayList<>();
        m = new ItemListPedidos.getItems();
        //ConsultadetallesPedidios();
       // consultar_mi_pedido_db(1);
    /*
        m.setCodigo_interno("001");
        m.setCodigo_alterno("AFG092");
        m.setDescripcion("MARTILLO");
        m.setPvp("10.00");
        m.setCantidad("30");
        m.setTotal("3");
        //  m.setStock("2000");

        model2.add(m);
        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("003");
        m.setCodigo_alterno("AFQW92");
        m.setDescripcion("RODILLO");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("3");
        // m.setStock("2100");
        model2.add(m);

        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("CLAVO 2.5\" ");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("3");
        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("tubo 2.5\" ");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("38");
        model2.add(m);
        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("desarmador 2.5\" ");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("34");
        model2.add(m);
        //-------------
        m = new ItemListPedidos.getItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("llave 2.5\" ");
        m.setPvp("15.00");
        m.setCantidad("30");
        m.setTotal("36");
        //  m.setStock("2100");

        model2.add(m);*/
        MyListaItemsPedidos(this,model2);
        AlertDialogConsultaCliente();
       // AlertDialogRegistraCliente();

        //Mis Lista de Items  ListProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        miListadePedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int id, long l) {
                try{
                    final ItemListPedidos.getItems item = (ItemListPedidos.getItems) adapterListPedido.getItem(id);
                        final int miItem =id;
                    Toast.makeText(MainActivity.this, "Item Seleccionado"+miItem, Toast.LENGTH_SHORT).show();

                    Log.w("Eliminar", "Esta Seleccionando el siguiente Item:"+item.getCodigo_interno()+" - "+item.getDescripcion());

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    final View mView = getLayoutInflater().inflate(R.layout.dialog_menu_item,null);
                    ImageButton btn_EditarItem = (ImageButton) mView.findViewById(R.id.img_btn_editar_menu_item);
                    ImageButton btn_EliminarItem = (ImageButton) mView.findViewById(R.id.img_btn_eliminar_menu_item);
                    TextView tv_eliminar_item = (TextView) mView.findViewById(R.id.txt_dm_item_eliminar);
                    TextView tv_editar_item = (TextView) mView.findViewById(R.id.txt_dm_editar_item);
                    mBuilder.setView(mView);
                    final AlertDialog dialogMenuItem = mBuilder.create();
                    dialogMenuItem.show();


                    tv_editar_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.w("Confirm_UpdateItemLista: ","Item: "+item.getIdItem());
                            Log.w("Confirm_UpdateItemLista: ","IDPedido: "+IdPedido.getText());
                            Confirm_UpdateItemLista(item.getIdItem(),IdPedido.getText().toString(), item.getDescripcion(), item.getCodigo_interno(), item.getCodigo_alterno(), item.getPvp(),item.getCantidad(), "0");
                            dialogMenuItem.dismiss();
                        }
                    });

                    btn_EditarItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.w("Confirm_UpdateItemLista: ","Item: "+item.getIdItem());
                            Log.w("Confirm_UpdateItemLista: ","IDPedido: "+IdPedido.getText());
                            Confirm_UpdateItemLista(item.getIdItem(),IdPedido.getText().toString(), item.getDescripcion(), item.getCodigo_interno(), item.getCodigo_alterno(), item.getPvp(),item.getCantidad(), "0");
                            dialogMenuItem.dismiss();
                            }
                    });
                    tv_eliminar_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                            dialogo1.setTitle("Mensaje!!!");
                            dialogo1.setMessage("Desea eliminar el Item?");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("Eliminando: ","ItemList: "+Integer.toString(id));
                                    model2.remove(miItem);
                                    Log.w("Eliminando de la Lista el: ","Item: "+item.getIdItem());
                                    eliminarItem(Integer.parseInt(item.getIdItem()));
                                    Log.w("Eliminando de la Base de datos: ","Item: "+item.getIdItem());
                                    MyListaItemsPedidos(MainActivity.this,model2);

                                    dialogMenuItem.dismiss();

                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("mensaje", "No pasa nada");

                                }
                            });
                            dialogo1.show();

                        }
                    });
                    btn_EliminarItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                            dialogo1.setTitle("Mensaje!!!");
                            dialogo1.setMessage("Desea eliminar el Item?");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("Eliminando: ","ItemList: "+Integer.toString(id));
                                    model2.remove(miItem);
                                    Log.w("Eliminando de la Lista el: ","Item: "+item.getIdItem());
                                    eliminarItem(Integer.parseInt(item.getIdItem()));
                                    Log.w("Eliminando de la Base de datos: ","Item: "+item.getIdItem());
                                    MyListaItemsPedidos(MainActivity.this,model2);

                                    dialogMenuItem.dismiss();
                                    //SumaTotales();
                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    Log.w("mensaje", "No pasa nada");

                                }
                            });
                            dialogo1.show();

                        }
                    });





                }catch (Exception e){
                    e.getMessage();
                    e.getStackTrace();
                    Log.w("Error", "error al Intentar eliminar el item:"+e.getMessage()+" e.getStackTrace() "+e.getStackTrace());

                }



            }
        });
        //
        img_add_items.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialogAddItems();
            }

        });






        btn_consulta_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.VISIBLE);
                FrameRegistraCliente.setVisibility(View.GONE);
                if(Edit_in_identificaion==null){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Ingrese Identificación");
                    return;

                }
                if(Edit_in_identificaion.length()<10){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "La indetificacion ingresada es invalida.");
                    return;

                }
                //ConsumeConsulta
                TextCodigoCliente.setVisibility(View.VISIBLE);
                TextNombreCliente.setVisibility(View.VISIBLE);
                ChkAgregarClinte.setVisibility(View.VISIBLE);


            }
        });
        TextMenu_ConsultarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.VISIBLE);
                FrameRegistraCliente.setVisibility(View.GONE);
            }
        });
        TextMenu_RegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.GONE);
                FrameRegistraCliente.setVisibility(View.VISIBLE);
            }
        });

        /**/
        lvProductos = (ListView) findViewById(R.id.lv_productos);
        //  ValidaPinAcceso acceso_pin = MaservenApplication.getApplication().getRestAdapter().create(ValidaPinAcceso.class);

        /* AdaptadorItems adapter = new AdaptadorItems(MainActivity.this, (List<Productos>) call);
                lvProductos.setAdapter(adapter);
                */
        /*http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=DES&cod_int=&cod_alt=&descripcion=bro&linea=
* http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=COI&cod_int=01020020014&cod_alt=&descripcion=&linea=*/
     /*   btn_Consultar_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ConsultaItemsWS("COI","01020020014","","","");
                ConsultaItemsWS("DES","","","bro","");
            }
        });
        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //Utils.generarAlerta(MainActivity.this, "Mensaje!", "Nuevo Pedido");



              if(!lb_newPedido){
                     AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Nuevo Pedido");
                dialogo1.setMessage("Está seguro de Crear un Nuevo Pedido...");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        crearPedido();
                        FrameNewPedido.setVisibility(View.VISIBLE);
                        FramePedidosDiariosOk.setVisibility(View.GONE);
                        lb_newPedido=true;
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        FrameNewPedido.setVisibility(View.GONE);
                        lb_newPedido=false;
                       // FramePedidosDiariosOk.setVisibility(View.GONE);
                    }
                });
                dialogo1.show();

                }else{

                  AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                  dialogo1.setTitle("Mensaje!!!");
                  dialogo1.setMessage("¿Desea salir de la Lista de Pedido?");
                  dialogo1.setCancelable(false);
                  dialogo1.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialogo1, int id) {
                          FrameNewPedido.setVisibility(View.GONE);
                          FramePedidosDiariosOk.setVisibility(View.VISIBLE);
                          lb_newPedido=false;
                          IdPedido.setText("");
                      }
                  });
                  dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialogo1, int id) {
                          Log.w("mensaje", "No pasa nada");

                      }
                  });
                  dialogo1.show();








                }



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(IdPedido.getText().toString().isEmpty()){

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Mensaje!!!");
                dialogo1.setMessage("¿Está seguro que desea salir de la aplicación?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                      //  super.onBackPressed();
                        finish();
                        Log.w("onBackPressed", "onBackPressed>> ---");
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Log.w("mensaje", "No pasa nada");

                    }
                });
                dialogo1.show();
            }else{
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Mensaje!!!");
                dialogo1.setMessage("¿Desea salir de la Lista de Pedido?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        FrameNewPedido.setVisibility(View.GONE);
                        FramePedidosDiariosOk.setVisibility(View.VISIBLE);
                        lb_newPedido=false;
                        IdPedido.setText("");
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Log.w("mensaje", "No pasa nada");

                    }
                });
                dialogo1.show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setTitle("Mensaje de Confirmación");
            dialogo1.setMessage("¿Está seguro de Abrir la pantalla de Configuraciones?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Abrir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Intent intent = new Intent(MainActivity.this, Configuraciones.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialogo1.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Toast.makeText(MainActivity.this, "Cerrando las configuraciones", Toast.LENGTH_SHORT).show();
                }
            });
            dialogo1.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.mis_clientes) {
            Fragment_cli.setVisibility(View.VISIBLE);
          /*  layout_ConsultarCliente.setVisibility(View.GONE);
            layout_RegistraCliente.setVisibility(View.GONE);

            TextMenu_RegistrarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    layout_ConsultarCliente.setVisibility(View.GONE);
                    layout_RegistraCliente.setVisibility(View.VISIBLE);
                }
            });
*/

            // Handle the camera action
  /*      } else*/ if (id == R.id.mis_productos) {

            AlertDialogConsultatems();
           // Fragment_cli.setVisibility(View.GONE);
           // FrameConsultaCliente.setVisibility(View.GONE);
           // FrameRegistraCliente.setVisibility(View.GONE);

        } /*else if (id == R.id.estatus_pedido) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);

        } else if (id == R.id.cambiar_contrasenia) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);

        }*/ else if (id == R.id.cerrar_sesion) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setTitle("Alerta!");
            dialogo1.setMessage("Esta seguro que desea cerrar sesión, si presiona ACEPTAR, se borraran todos sus datos de usuario, caso contraio presione CANCELAR.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Fragment_cli.setVisibility(View.GONE);
                    FrameConsultaCliente.setVisibility(View.GONE);
                    FrameRegistraCliente.setVisibility(View.GONE);
                    //Eliminar Datos antes de Cerrar Sesion
                    reset_accesos();
                    finish();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Log.w("mensaje", "No pasa nada");

                }
            });
            dialogo1.show();



        } else if (id == R.id.salir) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setTitle("Alerta!");
            dialogo1.setMessage("Esta seguro que desea salir de la aplicación");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Fragment_cli.setVisibility(View.GONE);
                    FrameConsultaCliente.setVisibility(View.GONE);
                    FrameRegistraCliente.setVisibility(View.GONE);
                    finish();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Log.w("mensaje", "No pasa nada");

                }
            });
            dialogo1.show();




        } else if (id == R.id.registra_cliente) {

            AlertDialogRegistraCliente();
                        //Toast.makeText(MainActivity.this, "Registra Cliente...", Toast.LENGTH_SHORT).show();

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void AlertDialogConsultaCliente(){
    img_consul_cli.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.consulta_cliente_dialog,null);
            //
            //
            String Identificacion;
            btnConsultaClienteOK = (Button) mView.findViewById(R.id.btnConsultaCliente);
            ListaMisClientes = (ListView) mView.findViewById(R.id.listClientesDialog);//ListaMisClientes
            final EditText editTIdentificacion = (EditText) mView.findViewById(R.id.edit_in_identificacion);
            final EditText editTNombreCliente = (EditText) mView.findViewById(R.id.edit_in_NombreCliente);
            progressBarListClientes = (ProgressBar) mView.findViewById(R.id.progressBar6);

            //
            dlgCodCliente = (TextView) mView.findViewById(R.id.textCodCliente);
            dlgRazonSocial = (TextView) mView.findViewById(R.id.textNombreCliente);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            btnConsultaClienteOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Usuario="";
                    if(editTIdentificacion.getText().toString().isEmpty() && editTNombreCliente.getText().toString().isEmpty()){
                        Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe ingresar datos para la consulta por identificación o Por Nombre del Cliente.");
                        return;
                    }
                    Usuario=SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,KEY_USER);

                    // ConsultaClienteWS(Usuario,editTIdentificacion.getText().toString());


                            if(!editTIdentificacion.getText().toString().isEmpty()){
                                progressBarListClientes.setVisibility(View.VISIBLE);
                                ConsultaClientesWS("RUC",Usuario,editTIdentificacion.getText().toString(),"");
                            }else if(!editTNombreCliente.getText().toString().isEmpty()){
                                progressBarListClientes.setVisibility(View.VISIBLE);
                                ConsultaClientesWS("RSO",Usuario,"",editTNombreCliente.getText().toString());
                            }

                    Log.w("CallConsulCliente", "ConsultaClienteWS ---");


                    //dialog.dismiss();
                }
            });
               //  miListadePedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ListaMisClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                    try{
                        final AllConsultaClientesWS.getClientes cli = (AllConsultaClientesWS.getClientes) adapter_mis_clientes.getItem(i);

                        Log.w("AllConsultaClientesWS", "Esta Seleccionando el siguiente Cliente:"+cli.getCodigo()+" - "+cli.getRazon_social());

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                        dialogo1.setTitle("Mensaje!!!");
                        dialogo1.setMessage("Desea Agregar este cliente para su pedido?");
                        dialogo1.setCancelable(false);
                        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {

                                //tv_TMIdentificacion tv_TMCliente np_identificacion np_cliente
                                np_identificacion.setText(cli.getIdentificacion());
                                np_cliente.setText(cli.getRazon_social());
                                int ID_Pedido_Interno=Integer.parseInt(IdPedido.getText().toString());
                                pedidos = Pedidos.findById(Pedidos.class,ID_Pedido_Interno);
                                pedidos.setNom_cliente(cli.getRazon_social());
                                pedidos.setIdent_cliente(cli.getIdentificacion());
                                pedidos.save();
                                consulta_mis_pedidos();

                            }
                        });
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Log.w("mensaje", "No pasa nada");

                            }
                        });
                        dialogo1.show();



                    }catch (Exception e){
                        e.getMessage();
                        e.getStackTrace();
                        Log.w("Error", "error al Intentar eliminar el item:"+e.getMessage()+" e.getStackTrace() "+e.getStackTrace());

                    }

                }
            });


        }

    });
}




    public void AlertDialogRegistraCliente(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.registra_cliente_dialog,null);
        //
        //

        rc_identificacion = (EditText) mView.findViewById(R.id.edit_r_identificacion);
        rc_nombre_cliente = (EditText) mView.findViewById(R.id.edit_r_razon_social);
        btn_registra_cliente =(Button) mView.findViewById(R.id.btnRegistrarCliente);
        btn_registra_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rc_identificacion.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe Ingresar la Identificación");
                    return;
                }
                if(rc_nombre_cliente.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe Ingresar el Nombre del Cliente");
                    return;
                }

                try{
                    clientes = new Clientes("",rc_identificacion.getText().toString(),rc_nombre_cliente.getText().toString().toUpperCase());
                    clientes.save();
                    Toast.makeText(MainActivity.this, "Cliente Registrado Correctamente...", Toast.LENGTH_SHORT).show();
                    rc_identificacion.setText("");
                    rc_nombre_cliente.setText("");
                }catch (Exception e){
                    e.getStackTrace();
                    Log.w("Error Database"," getStackTrace -->>"+e.getStackTrace());
                    Log.w("Error Database"," getMessage-->>"+e.getMessage());

                    Toast.makeText(MainActivity.this, "Problemas al registrar el Cliente...", Toast.LENGTH_SHORT).show();


                }
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void AlertDialogAddItems(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.add_productos_dialog,null);
        opciones = (Spinner) mView.findViewById(R.id.spinnerTipoConsulta);
        btnConsultaItem = (Button) mView.findViewById(R.id.btnConsultarItems);
        dato_consultar = (EditText) mView.findViewById(R.id.edt_dato_consultar);
        ListProductos = (ListView) mView.findViewById(R.id.add_prod_dialog_lv_lista_items);
        SpinnerAdapter sp_adapter = new SpinnerAdapter(lstSource,this);
        progresConsulItem = (ProgressBar) mView.findViewById(R.id.progressBar4);
        progresConsulItem.setVisibility(View.GONE);
        opciones.setAdapter(sp_adapter);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(this,lstSource.get(i),Toast.LENGTH_SHORT).show();
                Log.w("CallItems", "Esta Seleccionando el siguiente Tipo consulta:"+i+" - "+lstSource.get(i));
                TipoConsulta=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ListProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                   Items.getItems item = (Items.getItems) adapter.getItem(i);
                    Log.w("CallItems", "Esta Seleccionando el siguiente Item:"+item.getCodigo_interno()+" - "+item.getDescripcion());
                    Confirm_addItemLista(item.getDescripcion().toString(),item.getCodigo_interno(),item.getCodigo_alterno(),item.getPvp().toString(),item.getStock().toString());
                }catch (Exception e){
                    e.getStackTrace();
                    Log.w("CallItems", "Exception onItemClick: "+e.getMessage()+"-- msg"+e.getStackTrace());
                }
            }
        });
        btnConsultaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TipoConsulta==0){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe Seleccionar un tipo de Consulta");
                    return;
                }
                if(dato_consultar.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Agregar datos de consulta");
                    return;
                }
                progresConsulItem.setVisibility(View.VISIBLE);
                Log.w("CallItems", "ConsultaItemsWS: ");

                switch (TipoConsulta) {
                    case 1:
                       // Utils.generarAlerta(MainActivity.this, "ALERTA!", "Prueba: "+TipoConsulta);
                        ConsultaItemsWS("COI",dato_consultar.getText().toString(),"","","");
                        break;
                    case 2:
                       // Utils.generarAlerta(MainActivity.this, "ALERTA!", "Prueba: "+TipoConsulta);
                        ConsultaItemsWS("COA","",dato_consultar.getText().toString().toUpperCase(),"","");
                        break;
                    case 3:
                        Log.w("CallItems", "descripcion>>::" +dato_consultar.getText().toString().toUpperCase() );
                        ConsultaItemsWS("DES","","",dato_consultar.getText().toString().toUpperCase(),"");

                        break;
                    case 4:
                        ConsultaItemsWS("LIN","","","",dato_consultar.getText().toString().toUpperCase());
                        break;
                    default:

                }
                    Log.w("CallItems", "Registrando Descripcion2 ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));



            }
        });


    }
    /*http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=DES&cod_int=&cod_alt=&descripcion=bro&linea=
    * http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=COI&cod_int=01020020014&cod_alt=&descripcion=&linea=*/
    public void ConsultaItemsWS(String tipo, String cod_int, String cod_alt, String descripcion, String linea){
        Log.w("CallItems", "descripcion:::" +descripcion );
        Items items_servicios = MaservenApplication.getApplication().getRestAdapter().create(Items.class);
        try{
            CallItems = items_servicios.consumeItemsWS(tipo,cod_int,cod_alt,descripcion,linea);
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("CallItems", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // call.enqueue(new Callback<ApiResponseAppointments>()
        CallItems.enqueue(new Callback<Items.DatosItem>() {
            @Override
            public void onResponse(Call<Items.DatosItem> call, Response<Items.DatosItem> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("CallItems", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                Items.DatosItem otp = response.body();
                                if (otp.getCodigo_error().equals("1")){
                                    if(otp.getData().size()>0){
                                        Log.e("CallItems", otp.getData().get(0).getDescripcion());
                                        //Tv_Descripcion,Tv_Cod_Interno,Tv_Cod_alterno,Tv_PVP,Tv_Stock
                                        String Descripcion= otp.getData().get(0).getDescripcion();
                                        Log.e("CallItems","Descripcion: "+Descripcion);
                                        SharedPreferencesManager.setValor(MainActivity.this,PREFERENCIA_INICIO, Descripcion, DESCRIPCION_PROD);
                                        Log.w("CallItems", "Registrando Descripcion ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));
                                       // mustraDatos(Descripcion.toString());

                                       ArrayList<MItems> model = new ArrayList<>();
                                        MItems m = new MItems();


                                        Log.w("CallItems", "Size-> "+otp.getData().size());
                                        for (int s=0; s <otp.getData().size();s++){

                                            m = new MItems();
                                            m.setCodigo_interno(otp.getData().get(s).getCodigo_interno());
                                            m.setCodigo_alterno(otp.getData().get(s).getCodigo_alterno());
                                            m.setDescripcion(otp.getData().get(s).getDescripcion());
                                            m.setPvp(otp.getData().get(s).getPvp());
                                            m.setStock(otp.getData().get(s).getStock());
                                            model.add(m);
                                            Log.w("CallItems", "Descripción Item->"+s+": "+otp.getData().get(s).getDescripcion());


                                        }

                                        //model.add(otp.getData());

                                        adapter = new Adapter(getBaseContext(), otp.getData());
                                        ListProductos.setAdapter(adapter);
                                        progresConsulItem.setVisibility(View.GONE);


                                    }


                                    Log.w("CallItems >>", "ConsultaWS PIN:");
                                    /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();*/
                                    //  load_progreesbar.setVisibility(View.INVISIBLE);

                                }else{
                                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Usuario Incorrecto");
                                    // load_progreesbar.setVisse cayo
                                    // ibility(View.INVISIBLE);

                                    progresConsulItem.setVisibility(View.GONE);
                                }

                            } else {
                                Log.e("CallItems", "Error en el webservice, success false");
                                //load_progreesbar.setVisibility(View.INVISIBLE);

                            }
                        } else {
                            Log.e("CallItems", "Error de web service, no viene json");
                            // load_progreesbar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Log.e("CallItems", "Error en el webservice " + err);
                        //load_progreesbar.setVisibility(View.INVISIBLE);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";

                    progresConsulItem.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Problemas con el servicio,  comuquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                    Log.w("CallItems", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                    //load_progreesbar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Items.DatosItem> call, Throwable t) {
                Log.w("CallItems", "onFailure - "+t.getMessage());
                Toast.makeText(MainActivity.this, "Probelmas con la Conexión, intente en unos momentos", Toast.LENGTH_SHORT).show();

                progresConsulItem.setVisibility(View.GONE);
            }
        });

    }

    public void onClickConsultaItemsWS2(View v) {

        if(TipoConsulta==0){
            Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe Seleccionar un tipo de Consulta");
        }else{
            Log.w("CallItems", "ConsultaItemsWS: ");
            ConsultaItemsWS("DES","","","bro","");
            Log.w("CallItems", "Registrando Descripcion2 ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));


        }

       // Tv_Descripcion.setText(SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));
    }
    public void mustraDatos(String descripcion){
         Tv_Descripcion.setText(descripcion);

    }
    public void Confirm_addItemLista(String Descripcion, String CodInt, String CodAlt, String Pvp, String lvStock){

        if(Descripcion.contains("No Existen Datos")){
            Toast.makeText(MainActivity.this, "No existen datos", Toast.LENGTH_SHORT).show();
            return;
        }


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.confirm_dialog_items,null);
        //dialog_descripcion  dlg_codigo_alterno dlg_cod_interno dlg_pvp dlg_stock
        final TextView dlg_Descripcion = (TextView) mView.findViewById(R.id.dialog_descripcion);
        final TextView dlg_CodAlterno = (TextView) mView.findViewById(R.id.dlg_codigo_alterno);
        final TextView dlg_CodInterno= (TextView) mView.findViewById(R.id.dlg_cod_interno);
        final TextView dlg_PVP = (TextView) mView.findViewById(R.id.dlg_pvp);
        final EditText edDlg_pvp=(EditText)mView.findViewById(R.id.editDlg_pvp);
        TextView dlg_Stock = (TextView) mView.findViewById(R.id.dlg_stock);
         dlg_cantidad = (EditText) mView.findViewById(R.id.editAddCantidad);
        ImageButton btnagregarItem = (ImageButton) mView.findViewById(R.id.imgbtnAdditem);

        //
        dlg_Descripcion.setText(Descripcion);
        dlg_CodAlterno.setText(CodAlt);
        dlg_CodInterno.setText(CodInt);
        dlg_PVP.setText(Pvp);
        dlg_Stock.setText(lvStock);
        edDlg_pvp.setText(Pvp);



        mBuilder.setView(mView);
        final AlertDialog dialogCF = mBuilder.create();
        dialogCF.show();
        btnagregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor="";
                String Precio="",precio2="";
                double Cantidad=0;
                double precioUnitario=0, precioUnit2=0;
                double Total=0;
                Long idItem;
                if (dlg_cantidad.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe ingresar la cantidad");
                    return;
                }
                valor=dlg_cantidad.getText().toString();
                Cantidad = Integer.parseInt(valor);

                if (Cantidad==0){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "La cantidad ingresada debe ser mayor a 0.");
                    return;
                }
                Precio=edDlg_pvp.getText().toString();
                precio2 = dlg_PVP.getText().toString();
                precioUnitario= Double.parseDouble(Precio);
                precioUnit2= Double.parseDouble(precio2);

                Total=Cantidad*precioUnitario;
                boolean insertaItem=false;

                if(precioUnitario<precioUnit2){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "El precio debe ser mayor o igual al que se consulto.");
                    return;
                }
                //todo verifico si existe el Item antes de Insertar
                if(model2.size()>0){
                    for (int i=0;i<model2.size();i++){
                        if (model2.get(i).getCodigo_interno().equals(dlg_CodInterno.getText().toString())){
                            Log.w("CallItems", "Ya existe Item"+model2.get(i).getCodigo_interno()+" Comparando Item: "+dlg_CodInterno.getText().toString());
                            Utils.generarAlerta(MainActivity.this, "ALERTA!", "Este Producto ya existe");
                            insertaItem=true;//ya se encuentra el Item insertado en el pedido
                            break;
                        }

                    }
                    if(!insertaItem){
                        //todo agrega Item al Pedido
                        Log.w("CallItems", "Insertando.. "+dlg_CodInterno.getText().toString());
                        idItem =  registra_items_pedido(IdPedido.getText().toString(),"0","0",dlg_CodAlterno.getText().toString(),
                                dlg_CodInterno.getText().toString().toString(),"",dlg_Descripcion.getText().toString(),Double.toString(Cantidad),Double.toString(precioUnitario),Double.toString(Total*.12),Double.toString(Total+(Total*.12)),"A","N");

                        m = new ItemListPedidos.getItems();
                        m.setIdItem(Long.toString(idItem));
                        m.setCodigo_interno(dlg_CodInterno.getText().toString());
                        m.setCodigo_alterno(dlg_CodAlterno.getText().toString());
                        m.setDescripcion(dlg_Descripcion.getText().toString());
                        m.setPvp(Double.toString(precioUnitario));
                        m.setCantidad(Double.toString(Cantidad));
                        m.setTotal(Double.toString(Total));
                        model2.add(m);


                          Utils.generarAlerta(MainActivity.this, "Ok!", "Producto Agregado");
                    }

                }else{
                    //todo Agrega Nuevo Item al Pedido
                    Log.w("CallItems", "llenando la Lista con el Item"+dlg_CodInterno.getText().toString());
                    idItem = registra_items_pedido(IdPedido.getText().toString(),"0","0",dlg_CodAlterno.getText().toString(),
                            dlg_CodInterno.getText().toString().toString(),"",dlg_Descripcion.getText().toString(),Double.toString(Cantidad),Double.toString(precioUnitario),Double.toString(Total*.12),Double.toString(Total+(Total*.12)),"A","N");

                    m = new ItemListPedidos.getItems();
                    m.setIdItem(Long.toString(idItem));
                    m.setCodigo_interno(dlg_CodInterno.getText().toString());
                    m.setCodigo_alterno(dlg_CodAlterno.getText().toString());
                    m.setDescripcion(dlg_Descripcion.getText().toString());
                    m.setPvp(Double.toString(precioUnitario));
                    m.setCantidad(Double.toString(Cantidad));
                    m.setTotal(Double.toString(Total));
                    model2.add(m);

                     Utils.generarAlerta(MainActivity.this, "Ok!", "Producto Agregado");

                }


                try{
                    //todo Recarga la Lista de Pedidos
                   // MyListaItemsPedidos(MainActivity.this,model2); //comentado para validar la consulta de abajo
                   //consultando los items
                    SumaTotales();
                   // consulta_mi_lista_items_pedidos(IdPedido.getText().toString());

                }catch (Exception e){
                    e.getStackTrace();
                }

                dialogCF.dismiss();


            }
        });

    }
    void dismissDialog(View mView )    {
        //Now you can either use
        //dialogo_cancel.cancel();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.dismiss();
    }


    public void MyListaItemsPedidos(Activity activity, ArrayList<ItemListPedidos.getItems> model){

            adapterListPedido = new AdapterListPedidos(activity, model);
            miListadePedidos = (ListView) findViewById(R.id.MiListadePedidos);
            miListadePedidos.setAdapter(adapterListPedido);
    }

    public void ConsultaClienteWS(String Usuario, String identificacion){
        Log.w("CallConsulCliente", "ConsultaCliente");
        ConsultaCliente consulCliente = MaservenApplication.getApplication().getRestAdapter().create(ConsultaCliente.class);
        Log.w("CallConsulCliente", "acceso_login");
        try{
            CallConsulCliente = consulCliente.consumeDatosClienteWS(Usuario,identificacion);
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("CallConsulCliente", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("CallConsulCliente", "CallConsulCliente");
        CallConsulCliente.enqueue(new Callback<ConsultaCliente.getCliente>() {
            @Override
            public void onResponse(Call<ConsultaCliente.getCliente> call, Response<ConsultaCliente.getCliente> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("CallConsulCliente", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                ConsultaCliente.getCliente otp = response.body();


                                // Log.w("Acceso_usuario", "idusuario -> "+Usuario+ "");
                                if (otp.getCodigo_error().equals("1")){
                                    Log.w("CallConsulCliente", "identificacion -> "+otp.getIdentificacion() + "");
                                    Log.w("CallConsulCliente", "razon social -> "+otp.getRazon_social() + "");
                                    Log.w("CallConsulCliente", "response -> "+otp.getMensaje() + "");
                                    Log.w("CallConsulCliente", "codigo -> "+otp.getCodigo() + "");

                                    dlgRazonSocial.setText(otp.getRazon_social().toString());
                                    dlgCodCliente.setText(otp.getCodigo().toString());
                                    ma_Codigo.setText(otp.getCodigo().toString());
                                    ma_identificacion.setText(otp.getIdentificacion().toString());
                                    np_identificacion.setText(otp.getIdentificacion().toString());
                                    np_cliente.setText(otp.getRazon_social().toString());
                                    ma_cliente.setText(otp.getRazon_social().toString());
                                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Cliente Agregado ok!..");

                                }else{
                                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "problemas al Consultar");
                                    // load_progreesbar.setVisibility(View.INVISIBLE);
                                    //btn_iniciar.setEnabled(true);
                                }

                            } else {
                                Log.e("CallConsulCliente", "Error en el webservice, success false");
                                // load_progreesbar.setVisibility(View.INVISIBLE);
                                // btn_iniciar.setEnabled(true);
                            }
                        } else {
                            Log.e("CallConsulCliente", "Error de web service, no viene json");
                            // load_progreesbar.setVisibility(View.INVISIBLE);
                            // btn_iniciar.setEnabled(true);
                        }
                    } else {
                        Log.e("CallConsulCliente", "Error en el webservice " + err);
                        // load_progreesbar.setVisibility(View.INVISIBLE);
                        // btn_iniciar.setEnabled(true);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";
                    Log.w("CallConsulCliente", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                    // load_progreesbar.setVisibility(View.INVISIBLE);
                    // btn_iniciar.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ConsultaCliente.getCliente> call, Throwable t) {
                Log.w("CallConsulCliente", "onFailure - "+t.getMessage());
                //load_progreesbar.setVisibility(View.INVISIBLE);
            }
        });



        Log.w("CallConsulCliente", "Fin CallUser");

    }

    private void reset_accesos() {
        Log.w("Acceso_usuario", "Registrando USER ->");
        SharedPreferencesManager.deleteValor(this,PREFERENCIA_INICIO,KEY_USER);
        SharedPreferencesManager.deleteValor(this,PREFERENCIA_INICIO,KEY_PASS);
        SharedPreferencesManager.deleteValor(this,PREFERENCIA_INICIO,KEY_PIN);
        Log.w("Acceso_usuario", "Registrando PASS ->"+SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_USER));
        finish();
    }


    public void AlertDialogConsultatems(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.add_productos_dialog,null);
        opciones = (Spinner) mView.findViewById(R.id.spinnerTipoConsulta);
        btnConsultaItem = (Button) mView.findViewById(R.id.btnConsultarItems);
        progresConsulItem = (ProgressBar) mView.findViewById(R.id.progressBar4);
        progresConsulItem.setVisibility(View.GONE);

        dato_consultar = (EditText) mView.findViewById(R.id.edt_dato_consultar);
        ListProductos = (ListView) mView.findViewById(R.id.add_prod_dialog_lv_lista_items);
        SpinnerAdapter sp_adapter = new SpinnerAdapter(lstSource,this);

        opciones.setAdapter(sp_adapter);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(this,lstSource.get(i),Toast.LENGTH_SHORT).show();
                Log.w("CallItems", "Esta Seleccionando el siguiente Tipo consulta:"+i+" - "+lstSource.get(i));
                TipoConsulta=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ListProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    Items.getItems item = (Items.getItems) adapter.getItem(i);
                    Log.w("CallItems", "Esta Seleccionando el siguiente Item:"+item.getCodigo_interno()+" - "+item.getDescripcion());
                    //Confirm_addItemLista(item.getDescripcion().toString(),item.getCodigo_interno(),item.getCodigo_alterno(),item.getPvp().toString(),item.getStock().toString());
                }catch (Exception e){
                    e.getStackTrace();
                    Log.w("CallItems", "Exception onItemClick: "+e.getMessage()+"-- msg"+e.getStackTrace());
                }
            }
        });
        btnConsultaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TipoConsulta==0){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe Seleccionar un tipo de Consulta");
                    return;
                }
                if(dato_consultar.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Agregar datos de consulta");
                    return;
                }
                /*
                * lstSource.add("Código Interno");
        lstSource.add("Código Alterno");
        lstSource.add("Descripción");*/
                progresConsulItem.setVisibility(View.VISIBLE);
                Log.w("CallItems", "ConsultaItemsWS: ");
                switch (TipoConsulta) {
                    case 1:
                        // Utils.generarAlerta(MainActivity.this, "ALERTA!", "Prueba: "+TipoConsulta);
                        ConsultaItemsWS("COI",dato_consultar.getText().toString(),"","","");
                        break;
                    case 2:
                        // Utils.generarAlerta(MainActivity.this, "ALERTA!", "Prueba: "+TipoConsulta);
                        ConsultaItemsWS("COA","",dato_consultar.getText().toString().toUpperCase(),"","");
                        break;
                    case 3:
                        Log.w("CallItems", "descripcion>>::" +dato_consultar.getText().toString().toUpperCase() );
                        ConsultaItemsWS("DES","","",dato_consultar.getText().toString().toUpperCase(),"");

                        break;
                    case 4:
                        ConsultaItemsWS("LIN","","","",dato_consultar.getText().toString().toUpperCase());
                        break;
                    default:

                }
                Log.w("CallItems", "Registrando Descripcion2 ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));



            }
        });


    }
    public void crearPedido(){

      try{

          SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date myDate = new Date();
          String fecha_creacion = timeStampFormat.format(myDate);
          np_identificacion.setText("0000000000000");
          np_cliente.setText("CONSUMIDOR FINAL");

          pedidos = new Pedidos("0","0","0000000000000","CONSUMIDOR FINAL","0.00","0.00","0.00","0.00","A","N",fecha_creacion,SharedPreferencesManager.getValorEsperado(this,PREFERENCIA_INICIO,KEY_USER),"");
          long ID = pedidos.save();

          IdPedido.setText(Long.toString(ID));
          refresh_list_items("0");
          SumaTotales();

          /*pedidos = pedidos.findById(Pedidos.class, ID);
          pedidos.setNom_cliente("STALYN GRANDA");
          pedidos.setSincronizado("S");
          pedidos.save();*/

            //todo actualiza mi lista de pedidos
          consulta_mis_pedidos();
        Toast.makeText(MainActivity.this, "Nuevo Pedido...ID: "+ID+" >> "+fecha_creacion, Toast.LENGTH_SHORT).show();

         /* List<Pedidos> pedidoslist = Pedidos.listAll(Pedidos.class);


          for( Pedidos j : pedidoslist){
              //imprimimos el objeto pivote
              Log.w("Pedidos"," Id: "+j.getId()+
                      " NumPedido: "+j.getNum_pedido()+
                      " Numfactura: "+j.getNum_factura()+
                      " Ident: "+j.getIdent_cliente()+
                      " Nombre: "+j.getNom_cliente()+
                      " Valor:"+j.getVal_bruto()+
                      " Desc.: "+j.getVal_descuento()+
                      " Total: "+j.getVal_iva()+
                      " estado: "+j.getEstado()+
                      " Sincronizado.: "+j.getSincronizado());

          }*/


      }catch (Exception e){
        e.getStackTrace();
        Log.w("Error Database"," getStackTrace -->>"+e.getStackTrace());
        Log.w("Error Database"," getMessage-->>"+e.getMessage());

        Toast.makeText(MainActivity.this, "Problemas al registrar Pedido...", Toast.LENGTH_SHORT).show();


    }

    }
    public void consulta_mis_pedidos(){
        try{
            pedidos_arrayList = new ArrayList<>();
            pedidos = new Pedidos();
            //pedidos_List = Pedidos.listAll(Pedidos.class);
            pedidos_List = Pedidos.find(Pedidos.class,"ESTADO = ? ","A");

            //for(int p=0;p<pedidos_List.size();p++){
            for(int p=pedidos_List.size()-1;p>=0;p--){
                pedidos = pedidos_List.get(p);
                pedidos_arrayList.add(pedidos);
            }
            adapter_mis_pedidos = new AdapterListMisPedidos(getBaseContext(), pedidos_arrayList);
            mis_pedidos_diarios_listv.setAdapter(adapter_mis_pedidos);

            /*mis_pedidos_diarios_listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pedidos = (Pedidos) adapter_mis_pedidos.getItem(i);

                    Log.w("Onclick", "Esta Seleccionando el siguiente Item:"+pedidos.getId()+" - "+pedidos.getNom_cliente());

                }
            });*/


        }catch (Exception e){
            e.getStackTrace();
            Log.w("Error Database"," getStackTrace -->>"+e.getStackTrace());
            Log.w("Error Database"," getMessage-->>"+e.getMessage());

            Toast.makeText(MainActivity.this, "Problemas al consultar...", Toast.LENGTH_SHORT).show();


        }
    }

public long registra_items_pedido(String idItem, String num_pedido, String num_fact, String cod_alter, String cod_inter,
                                  String linea, String descripcion, String cantidad, String valor, String iva, String total, String estado, String sincronizado
                                  ) {
    long ITEM = 0;
    try {
        //todo REGISTRA ITEMS DEl PEDIDO EN LA BASE DE DATOS
        detallepedido = new DetallePedidos(idItem, num_pedido, num_fact, cod_alter, cod_inter, linea, descripcion, cantidad, valor, iva, total, estado, sincronizado);
        ITEM = detallepedido.save();
        Log.w("ItemPedidos", " Id: " +idItem +
                " Pedido: " + num_pedido +
                " NumPedido: " + num_pedido +
                " Numfactura: " + num_fact +
                " descr: " + descripcion +
                " cod alt: " + cod_alter +
                " cod int:" + cod_inter +
                " cantidad:" + cantidad +
                " valor.: " + valor +
                " estado: " + estado +
                " total: " + total +
                " Sincronizado.: " + sincronizado);

        Toast.makeText(MainActivity.this, "Item guardado: " + ITEM + " >> ", Toast.LENGTH_SHORT).show();

      /*  List<DetallePedidos> detpedidoslist = DetallePedidos.listAll(DetallePedidos.class);


        for (DetallePedidos j : detpedidoslist) {
            //imprimimos el objeto pivote
            Log.w("ItemPedidos", " Id: " + j.getId() +
                    " Pedido: " + j.getId_pedido() +
                    " NumPedido: " + j.getNum_pedido() +
                    " Numfactura: " + j.getNum_fact() +
                    " descr: " + j.getDescripcion() +
                    " cod alt: " + j.getCod_alter() +
                    " cod int:" + j.getCod_inter() +
                    " valor.: " + j.getValor() +
                    " iva: " + j.getIva() +
                    " total: " + j.getTotal() +
                    " Sincronizado.: " + j.getSincronizado());

        }*/


    } catch (Exception e) {
        e.getStackTrace();
        Log.w("Error Database", " getStackTrace -->>" + e.getStackTrace());
        Log.w("Error Database", " getMessage-->>" + e.getMessage());

        Toast.makeText(MainActivity.this, "Problemas al registrar los Items Pedido...", Toast.LENGTH_SHORT).show();


    }
    return ITEM;
}
public  void consulta_mi_lista_items_pedidos(String id_pedido){

    try{

        arrayList_detalles_pedidos = new ArrayList<>();
        detallepedido = new DetallePedidos();
        list_detalles_pedidos = DetallePedidos.listAll(DetallePedidos.class);


        for(int p=list_detalles_pedidos.size()-1;p>=0;p--){
            if(list_detalles_pedidos.get(p).getId_pedido().equals(id_pedido)){
                detallepedido = list_detalles_pedidos.get(p);
                arrayList_detalles_pedidos.add(detallepedido);

            }

        }

        adapter_list_det_pedido =  new AdapterListMiDetPedido(getBaseContext(), arrayList_detalles_pedidos);
        miListadePedidos = (ListView) findViewById(R.id.MiListadePedidos);
        miListadePedidos.setAdapter(adapterListPedido);


/*
        List<DetallePedidos> detpedidoslist = DetallePedidos.listAll(DetallePedidos.class);


        for( DetallePedidos j : detpedidoslist){
            //imprimimos el objeto pivote
            Log.w("ItemPedidos"," Id: "+j.getId()+
                    " Pedido: "+j.getId_pedido()+
                    " NumPedido: "+j.getNum_pedido()+
                    " Numfactura: "+j.getNum_fact()+
                    " descr: "+j.getDescripcion()+
                    " cod alt: "+j.getCod_alter()+
                    " cod int:"+j.getCod_inter()+
                    " valor.: "+j.getValor()+
                    " iva: "+j.getIva()+
                    " total: "+j.getTotal()+
                    " Sincronizado.: "+j.getSincronizado());

        }*/


    }catch (Exception e){
        e.getStackTrace();
        Log.w("Error Database"," getStackTrace -->>"+e.getStackTrace());
        Log.w("Error Database"," getMessage-->>"+e.getMessage());

        Toast.makeText(MainActivity.this, "Problemas al consultar los Items..", Toast.LENGTH_SHORT).show();


    }

}

public void SumaTotales(){
    double sumSubtotal=0, cant=0,pvp=0, sumTotal=0, sumDesc=0,sumIVA=0,descuent=0;
    String iva="",total="",subtotal="", descuento="";
    DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
    simbolos.setDecimalSeparator('.');
    DecimalFormat formateador = new DecimalFormat("####.##",simbolos);
    if(model2.size()>0){
        for (int i=0;i<model2.size();i++){
           // sumSubtotal=sumSubtotal+model2.get(i).getPvp();
            pvp = Double.parseDouble(model2.get(i).getPvp());
            cant =  Double.parseDouble(model2.get(i).getCantidad());
            sumSubtotal =sumSubtotal + (pvp*cant);

        }

        if(edt_ma_desc.getText().toString().isEmpty()){
            descuent = Double.parseDouble("0");
        } else {
            descuent = Double.parseDouble(edt_ma_desc.getText().toString());
        }




        sumIVA = (sumSubtotal-descuent)*0.12;
        sumTotal = sumSubtotal-descuent+sumIVA;


        Log.w("formateador>>"," sumIVA-->>"+formateador.format(sumIVA));

        iva=formateador.format(sumIVA);
        total=formateador.format(sumTotal);
        subtotal=formateador.format(sumSubtotal);
        descuento=formateador.format(descuent);
        /*
        tv_ma_subtotal.setText(Double.toString(sumSubtotal));
        tv_ma_iva.setText(Double.toString(sumIVA));
        tv_ma_total.setText(Double.toString(sumTotal));*/

        tv_ma_subtotal.setText(subtotal);
        tv_ma_iva.setText(iva);
        tv_ma_total.setText(total);


    }else{

        iva=formateador.format(sumIVA);
        total=formateador.format(sumTotal);
        subtotal=formateador.format(sumSubtotal);
        descuento=formateador.format(descuent);

        tv_ma_subtotal.setText(subtotal);
        tv_ma_total.setText(total);
        tv_ma_iva.setText(iva);
        edt_ma_desc.setText(descuento);
    }
    String pedInter=IdPedido.getText().toString();
    int pedidoInterno;
    pedidoInterno= Integer.parseInt(pedInter);
    pedidos = Pedidos.findById(Pedidos.class,pedidoInterno);
    pedidos.setVal_bruto(subtotal);
    pedidos.setVal_descuento(descuento);
    pedidos.setVal_iva(iva);
    pedidos.setVal_total(total);
    pedidos.save();
    consulta_mis_pedidos();
}


    public void ConsultaClientesWS(String tipo, String usuario, String identificacion, String nombre_cliente){

        Log.w("ConsultaClientesWS", "nombre_cliente:::" +nombre_cliente );
        AllConsultaClientesWS clientes_servicios = MaservenApplication.getApplication().getRestAdapter().create(AllConsultaClientesWS.class);
        try{
            CallClientes = clientes_servicios.consumeClientesWS(tipo,usuario,identificacion,nombre_cliente);
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("ConsultaClientesWS", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // call.enqueue(new Callback<ApiResponseAppointments>()
        CallClientes.enqueue(new Callback<AllConsultaClientesWS.DatosCliente>() {
            @Override
            public void onResponse(Call<AllConsultaClientesWS.DatosCliente> call, Response<AllConsultaClientesWS.DatosCliente> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("ConsultaClientesWS", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                AllConsultaClientesWS.DatosCliente otp = response.body();

                                if (otp.getData().size()>0){


                                        ArrayList<AllConsultaClientesWS.getClientes> MClientes = new ArrayList<>();
                                        AllConsultaClientesWS.getClientes cli = new AllConsultaClientesWS.getClientes();


                                        Log.w("ConsultaClientesWS", "Size-> "+otp.getData().size());
                                        for (int s=0; s <otp.getData().size();s++){

                                            cli = new AllConsultaClientesWS.getClientes();
                                            cli.setCodigo(otp.getData().get(s).getCodigo());// .setCodigo_interno(otp.getData().get(s).getCodigo_interno());
                                            cli.setIdentificacion(otp.getData().get(s).getIdentificacion()); //.setCodigo_alterno(otp.getData().get(s).getCodigo_alterno());
                                            cli.setRazon_social(otp.getData().get(s).getRazon_social()); //.setDescripcion(otp.getData().get(s).getDescripcion());

                                            MClientes.add(cli);
                                            Log.w("ConsultaClientesWS", "Descripción Item->"+s+": "+otp.getData().get(s).getRazon_social());
                                        }
                                         adapter_mis_clientes = new AdapterMisClientes(getBaseContext(), otp.getData());
                                         ListaMisClientes.setAdapter(adapter_mis_clientes);

                                    progressBarListClientes.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(MainActivity.this, "No trae datos la consulta", Toast.LENGTH_SHORT).show();
                                    progressBarListClientes.setVisibility(View.GONE);
                                }

                            } else {
                                Log.e("ConsultaClientesWS", "Error en el webservice, success false");

                            }
                        } else {
                            Log.e("ConsultaClientesWS", "Error de web service, no viene json");
                            // load_progreesbar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Log.e("ConsultaClientesWS", "Error en el webservice " + err);
                        //load_progreesbar.setVisibility(View.INVISIBLE);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";

                    progressBarListClientes.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Problemas con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                    Log.w("ConsultaClientesWS", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());

                }
            }

            @Override
            public void onFailure(Call<AllConsultaClientesWS.DatosCliente> call, Throwable t) {
                Log.w("ConsultaClientesWS", "onFailure - "+t.getMessage());
                Toast.makeText(MainActivity.this, "Probelmas con la Conexión, intente en unos momentos", Toast.LENGTH_SHORT).show();
               progressBarListClientes.setVisibility(View.GONE);
            }
        });

    }
    public  void ConsultadetallesPedidios(String IdPedidoInterno, String PedidoExterno){

        try {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = new Date();
            String fecha_creacion = timeStampFormat.format(myDate);

            Log.w("list_detalles_pedidos", "IdPedido "+IdPedidoInterno);
              list_detalles_pedidos = DetallePedidos.find(DetallePedidos.class,"IDPEDIDO = ? AND ESTADO = ? AND SINCRONIZADO = ?",IdPedidoInterno,"A","N");
            Log.w("list_detalles_pedidos", "Cantidad de Registros en el detalle "+list_detalles_pedidos.size());
                for (int i = 0; i < list_detalles_pedidos.size(); i++) {

                Log.w("list_detalles_pedidos", "Detalles - ID " + list_detalles_pedidos.get(i).getId() + " Id_pedido_intero " + list_detalles_pedidos.get(i).getId_pedido() + " Cod_alter " +
                        list_detalles_pedidos.get(i).getCod_alter() + " Cod_inter " +
                        list_detalles_pedidos.get(i).getCod_inter() + " Descripcion " +
                        list_detalles_pedidos.get(i).getDescripcion() + " Cantidad " +
                        list_detalles_pedidos.get(i).getCantidad() + " Valor " +
                        list_detalles_pedidos.get(i).getValor() + " Total " +
                        list_detalles_pedidos.get(i).getTotal() + " Estado " +
                        list_detalles_pedidos.get(i).getEstado());
                    if(list_detalles_pedidos.get(i).getEstado().toString().equals("A")){
                        //enviar a detalle
                        RegistraDetallePedidosWS(PedidoExterno,
                                list_detalles_pedidos.get(i).getCod_inter(),
                                list_detalles_pedidos.get(i).getCod_alter(),
                                list_detalles_pedidos.get(i).getCantidad(),
                                list_detalles_pedidos.get(i).getValor(),
                                fecha_creacion,
                                fecha_creacion,
                                list_detalles_pedidos.get(i).getEstado(),
                                "0");
                    }

                 }
        }catch (Exception e){
            e.getStackTrace();

            Log.w("Error en la Consulta DetallePedidos", "Exception: "+e.getMessage());
        }

    }


    public void consultar_mi_pedido_db(int ID){
        try {

            pedidos = new Pedidos();
            pedidos = Pedidos.findById(Pedidos.class,ID);
            pedidos.getId();
            pedidos.getNom_cliente();
            pedidos.getIdent_cliente();
            pedidos.getVal_bruto();
            pedidos.getVal_descuento();
            pedidos.getVal_iva();
            pedidos.getVal_total();
            Log.w("Consulta Pedidos: ", "getId: "+pedidos.getId()+" Cliente. "+pedidos.getNom_cliente() );
            FrameNewPedido.setVisibility(View.VISIBLE);
            FramePedidosDiariosOk.setVisibility(View.GONE);
            lb_newPedido=true;

            np_identificacion.setText(pedidos.getIdent_cliente().toString());
            np_cliente.setText(pedidos.getNom_cliente().toString());
            IdPedido.setText(pedidos.getId().toString());
            tv_ma_subtotal.setText(pedidos.getVal_bruto().toString());
            tv_ma_total.setText(pedidos.getVal_total().toString());
            tv_ma_iva.setText(pedidos.getVal_iva().toString());
            edt_ma_desc.setText(pedidos.getVal_descuento().toString());

            if(!pedidos.getId().toString().isEmpty()){
                Log.w("Consulta Pedidos", "IdPedido "+pedidos.getId().toString());
                list_detalles_pedidos = DetallePedidos.find(DetallePedidos.class,"IDPEDIDO = ? AND ESTADO = ? AND SINCRONIZADO = ?",pedidos.getId().toString(),"A","N");
                if(list_detalles_pedidos.size()>0){
                    model2.clear();
                    for (int i = 0; i < list_detalles_pedidos.size(); i++) {

                        Log.w("Consulta Pedidos", "Detalles - ID " + list_detalles_pedidos.get(i).getId() + " Id_pedido " + list_detalles_pedidos.get(i).getId_pedido() + " Cod_alter " +
                                list_detalles_pedidos.get(i).getCod_alter() + " Cod_inter " +
                                list_detalles_pedidos.get(i).getCod_inter() + " Descripcion " +
                                list_detalles_pedidos.get(i).getDescripcion() + " Cantidad " +
                                list_detalles_pedidos.get(i).getCantidad() + " Valor " +
                                list_detalles_pedidos.get(i).getValor() + " Total " +
                                list_detalles_pedidos.get(i).getTotal()+" estado: "+list_detalles_pedidos.get(i).getEstado());
                        if(list_detalles_pedidos.get(i).getEstado().toString().equals("A")){
                            ItemListPedidos.getItems m = new ItemListPedidos.getItems();
                            m.setIdItem(list_detalles_pedidos.get(i).getId().toString());
                            m.setCodigo_interno(list_detalles_pedidos.get(i).getCod_inter());
                            m.setCodigo_alterno(list_detalles_pedidos.get(i).getCod_alter());
                            m.setDescripcion(list_detalles_pedidos.get(i).getDescripcion());
                            m.setPvp(list_detalles_pedidos.get(i).getValor());
                            m.setCantidad(list_detalles_pedidos.get(i).getCantidad() );
                            m.setTotal(list_detalles_pedidos.get(i).getTotal());
                            model2.add(m);


                        }


                    }
                }else{
                    model2.clear();
                }

                miListadePedidos.deferNotifyDataSetChanged();
                adapterListPedido = new AdapterListPedidos(this, model2);
                miListadePedidos = (ListView) findViewById(R.id.MiListadePedidos);
                miListadePedidos.setAdapter(adapterListPedido);


            }


            Toast.makeText(MainActivity.this, "Consultando mi Pedido N°:"+ID, Toast.LENGTH_SHORT).show();



        }catch (Exception e){
            e.getStackTrace();

            Log.w("Error en la Consulta DetallePedidos", "Exception: "+e.getMessage());
        }


    }
    public void eliminarItem(int Id){

        try{
            detallepedido = new DetallePedidos();
            detallepedido = DetallePedidos.findById(DetallePedidos.class,Id);
            detallepedido.setEstado("I");
            detallepedido.save();
            SumaTotales();
            Toast.makeText(MainActivity.this, "El item N°:"+Id+" ha sido Eliminado exitosamente...", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.getStackTrace();

            Log.w("EliminarItem DetallePedidos", "Exception: "+e.getMessage());
            Toast.makeText(MainActivity.this, "Problemas al momento de Eliminar el Item, comuniquese con SISTEMAS", Toast.LENGTH_SHORT).show();

        }





    }
    public void Confirm_UpdateItemLista(final String Id_item, final String ID, String Descripcion, String CodInt, String CodAlt, String Pvp, String cant, String lvStock){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.confirm_dialog_items,null);
        //dialog_descripcion  dlg_codigo_alterno dlg_cod_interno dlg_pvp dlg_stock
        final TextView dlg_Descripcion = (TextView) mView.findViewById(R.id.dialog_descripcion);
        final TextView dlg_CodAlterno = (TextView) mView.findViewById(R.id.dlg_codigo_alterno);
        final TextView dlg_CodInterno= (TextView) mView.findViewById(R.id.dlg_cod_interno);
        final TextView dlg_PVP = (TextView) mView.findViewById(R.id.dlg_pvp);
        final EditText edDlg_pvp=(EditText)mView.findViewById(R.id.editDlg_pvp);
        TextView dlg_Stock = (TextView) mView.findViewById(R.id.dlg_stock);
        dlg_cantidad = (EditText) mView.findViewById(R.id.editAddCantidad);
        ImageButton btnagregarItem = (ImageButton) mView.findViewById(R.id.imgbtnAdditem);

        //
        dlg_Descripcion.setText(Descripcion);
        dlg_CodAlterno.setText(CodAlt);
        dlg_CodInterno.setText(CodInt);
        dlg_PVP.setText(Pvp);
        dlg_Stock.setText(lvStock);
        edDlg_pvp.setText(Pvp);


        dlg_cantidad.setText(cant);
        mBuilder.setView(mView);
        final AlertDialog dialogCF = mBuilder.create();
        dialogCF.show();
        btnagregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor="";
                String Precio="",precio2="";
                double Cantidad=0;
                double precioUnitario=0, precioUnit2=0;
                double Total=0;
                Long idItem;
                if (dlg_cantidad.getText().toString().isEmpty()){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Debe ingresar la cantidad");
                    return;
                }
                valor=dlg_cantidad.getText().toString();
                Cantidad = Integer.parseInt(valor);

                if (Cantidad==0){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "La cantidad ingresada debe ser mayor a 0.");
                    return;
                }
                Precio=edDlg_pvp.getText().toString();
                precio2 = dlg_PVP.getText().toString();
                precioUnitario= Double.parseDouble(Precio);
                precioUnit2= Double.parseDouble(precio2);

                Total=Cantidad*precioUnitario;


                if(precioUnitario<precioUnit2){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "El precio debe ser mayor o igual al que se consulto.");
                    return;
                }

                Log.w("Integer.parseInt(Id_item)", "Modificar: "+Integer.parseInt(Id_item));

                /*model2.get(Integer.parseInt(Id_item)).setCantidad(valor);
                model2.get(Integer.parseInt(Id_item)).setPvp(precio2);
                model2.get(Integer.parseInt(Id_item)).setTotal(Double.toString(Total));*/

                updateListItem(Id_item,valor,precio2,Double.toString(Total));
                refresh_list_items(ID);

                try{
                    //todo Recarga la Lista de Pedidos
                    // MyListaItemsPedidos(MainActivity.this,model2); //comentado para validar la consulta de abajo
                    //consultando los items
                    SumaTotales();
                    // consulta_mi_lista_items_pedidos(IdPedido.getText().toString());

                }catch (Exception e){
                    e.getStackTrace();
                }

                dialogCF.dismiss();


            }
        });

    }

    public void updateListItem(String Iditem, String Cantidad, String Valor, String Total){
       try{
           int Id = Integer.parseInt(Iditem);
        detallepedido = new DetallePedidos();
        detallepedido = DetallePedidos.findById(DetallePedidos.class,Id);
        detallepedido.setCantidad(Cantidad);
        detallepedido.setValor(Valor);
        detallepedido.setTotal(Total);
        detallepedido.save();
           Log.w("updateListItem", "Actualizado Correctamente el Item..."+Id);
           Toast.makeText(MainActivity.this, "Item  N°: "+Id+" Actualizado exitosamente", Toast.LENGTH_SHORT).show();

       }catch (Exception e){
        e.getStackTrace();

        Log.w("updateListItem", "Exception: "+e.getMessage());
           Toast.makeText(MainActivity.this, "Problemas al actualizar el Item, comuniquese con SISTEMAS", Toast.LENGTH_SHORT).show();
    }


}

public void refresh_list_items(String IdPedido){

    if(!pedidos.getId().toString().isEmpty()){
        Log.w("Consulta Pedidos", "IdPedido >>> "+IdPedido);
        list_detalles_pedidos = DetallePedidos.find(DetallePedidos.class,"IDPEDIDO = ? AND ESTADO = ? ",IdPedido,"A");
        if(list_detalles_pedidos.size()>0){
            model2.clear();
            for (int i = 0; i < list_detalles_pedidos.size(); i++) {

                Log.w("Consulta Pedidos", "Detalles - ID " + list_detalles_pedidos.get(i).getId() + " Id_pedido " + list_detalles_pedidos.get(i).getId_pedido() + " Cod_alter " +
                        list_detalles_pedidos.get(i).getCod_alter() + " Cod_inter " +
                        list_detalles_pedidos.get(i).getCod_inter() + " Descripcion " +
                        list_detalles_pedidos.get(i).getDescripcion() + " Cantidad " +
                        list_detalles_pedidos.get(i).getCantidad() + " Valor " +
                        list_detalles_pedidos.get(i).getValor() + " Total " +
                        list_detalles_pedidos.get(i).getTotal()+" estado: "+list_detalles_pedidos.get(i).getEstado());
                if(list_detalles_pedidos.get(i).getEstado().toString().equals("A")){
                    ItemListPedidos.getItems m = new ItemListPedidos.getItems();
                    m.setIdItem(list_detalles_pedidos.get(i).getId().toString());
                    m.setCodigo_interno(list_detalles_pedidos.get(i).getCod_inter());
                    m.setCodigo_alterno(list_detalles_pedidos.get(i).getCod_alter());
                    m.setDescripcion(list_detalles_pedidos.get(i).getDescripcion());
                    m.setPvp(list_detalles_pedidos.get(i).getValor());
                    m.setCantidad(list_detalles_pedidos.get(i).getCantidad() );
                    m.setTotal(list_detalles_pedidos.get(i).getTotal());
                    model2.add(m);


                }


            }
        }else{
            model2.clear();
        }

        miListadePedidos.deferNotifyDataSetChanged();
        adapterListPedido = new AdapterListPedidos(this, model2);
        miListadePedidos = (ListView) findViewById(R.id.MiListadePedidos);
        miListadePedidos.setAdapter(adapterListPedido);


    }

}

    public void eliminarPedido(int Id){

        try{
            pedidos = new Pedidos();
            pedidos = Pedidos.findById(Pedidos.class,Id);
            pedidos.setEstado("I");
            pedidos.save();
            Toast.makeText(MainActivity.this, "Pedido N°: "+Id+" Eliminado exitosamente", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.getStackTrace();

            Log.w("eliminarPedido", "Exception: "+e.getMessage());
        }
    }

    //edt_ma_desc.OnKeyListener(new View.OnKeyListener)

/**
 * http://localhost:7777/MaservenWS/app/RegistraPedidosWS?num_pedido=232&identificacion=0100707207001&numero_ped_app=2&total_pedido=112&subtotal=100&valor_dscto=0&valor_iva=12&email=bareca90@gmail.com
 */

public void enviarPedidoID(int Id){

    try{


        pedidos = new Pedidos();
        pedidos = Pedidos.findById(Pedidos.class,Id);
        registraPedidosCab(pedidos.getId().toString(),
                            pedidos.getIdent_cliente().toString(),
                            pedidos.getId().toString(),
                            pedidos.getVal_total().toString(),
                            pedidos.getVal_bruto().toString(),
                            pedidos.getVal_descuento().toString(),
                            pedidos.getVal_iva().toString(),
                            SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER));

        Toast.makeText(MainActivity.this, "Pedido N°: "+Id+" Enviado...", Toast.LENGTH_SHORT).show();
        //// TODO: 7/2/2018 Borrar esta opcion es temporal
       /*pedidos = new Pedidos();
        pedidos = Pedidos.findById(Pedidos.class,Id);
        pedidos.setNum_pedido("29002");
        pedidos.setSincronizado("S");
        pedidos.save();
        consulta_mis_pedidos();
        progres_sync.setVisibility(View.GONE);
        btn_sync.setVisibility(View.VISIBLE);*/

    }catch (Exception e){
        e.getStackTrace();
        btn_sync.setEnabled(true);
        //progres_sync.setVisibility(View.INVISIBLE);
        Log.w("eliminarPedido", "Exception: "+e.getMessage());
    }
}


public void registraPedidosCab(final String num_pedido,
                               String identificacion,
                               String numero_ped_app,
                               String total_pedido,
                               String subtotal,
                               String valor_dscto,
                               String valor_iva,
                               String email){


    Log.w("registraPedidosCab", "identificacion:::" +identificacion );
    SyncPedidosWS sync_pedidos = MaservenApplication.getApplication().getRestAdapter().create(SyncPedidosWS.class);
    try{
        callInsertPedidos = sync_pedidos.setDatosPedidosWS(num_pedido,identificacion,numero_ped_app,total_pedido,subtotal,valor_dscto,valor_iva,email);
    } catch (IllegalArgumentException e1) {
        Log.w("registraPedidosCab", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
        e1.printStackTrace();
    } catch (IllegalStateException e1) {
        e1.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    callInsertPedidos.enqueue(new Callback<SyncPedidosWS.DatosPedidos>() {
        @Override
        public void onResponse(Call<SyncPedidosWS.DatosPedidos> call, Response<SyncPedidosWS.DatosPedidos> response) {
            String err = "";
            try{
                Log.w("registraPedidosCab", "Consultando respuesta" +err );
                if (err.equalsIgnoreCase("")) {
                    if (response.body() != null) {
                        if (response.isSuccess()) {
                            SyncPedidosWS.DatosPedidos otp = response.body();

                            if (otp.getId_pedido().equals("-1")){
                                Toast.makeText(MainActivity.this, "Problemas al sincronizar el Pedido", Toast.LENGTH_SHORT).show();

                            }else{
                                pedidos = new Pedidos();
                                pedidos = Pedidos.findById(Pedidos.class,Integer.parseInt(num_pedido.toString()));
                                pedidos.setNum_pedido(otp.getId_pedido());
                                pedidos.setSincronizado("S");
                                pedidos.save();
                                Log.e("registraPedidosCab", "Comenzando a Sincronizar el detalle del pedido");
                                Log.e("registraPedidosCab", "Pedido Interno:"+num_pedido.toString()+" Pedido externo: "+otp.getId_pedido().toString());
                                ConsultadetallesPedidios(num_pedido.toString(),otp.getId_pedido().toString());
                                Log.e("registraPedidosCab", "Enviando el Correo - IDPedido: "+otp.getId_pedido().toString()+" Nombre Archivo: "+otp.getNombre_archivo().toString()+"email: stalyn.granda@hotmail.com");
                                GenerarDocumento(otp.getId_pedido().toString(),otp.getNombre_archivo().toString(),"stalyn.granda@hotmail.com");
                                progres_sync.setVisibility(View.GONE);
                                btn_sync.setVisibility(View.VISIBLE);
                                consulta_mis_pedidos();
                                Toast.makeText(MainActivity.this, "Pedido sincronizado Correctamente", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.e("registraPedidosCab", "Error en el webservice, success false");
                            btn_sync.setEnabled(true);
                            //progres_sync.setVisibility(View.INVISIBLE);
                        }

                    }else {
                        Log.e("registraPedidosCab", "Error de web service, no viene json");
                        //btn_sync.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Problemas1 con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                        progres_sync.setVisibility(View.GONE);
                        btn_sync.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e("registraPedidosCab", "Error en el webservice " + err);
                    progres_sync.setVisibility(View.GONE);
                    btn_sync.setVisibility(View.VISIBLE);
                }


            } catch (Exception e) {


                Toast.makeText(MainActivity.this, "Problemas con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                Log.w("registraPedidosCab", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                progres_sync.setVisibility(View.GONE);
                btn_sync.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void onFailure(Call<SyncPedidosWS.DatosPedidos> call, Throwable t) {
            Log.w("registraPedidosCab", "onFailure - "+t.getMessage());
            Toast.makeText(MainActivity.this, "Probelmas con la Conexión, intente en unos momentos", Toast.LENGTH_SHORT).show();
            progres_sync.setVisibility(View.GONE);
            btn_sync.setVisibility(View.VISIBLE);
        }
    });

}

//ConsultadetallesPedidios(ID);
  public void RegistraDetallePedidosWS(final String num_pedido,
                                       String codigo_interno,
                                       String codigo_alterno,
                                       String cantidad,
                                       String precio,
                                       String fecha_creacion,
                                       String fecha_actualizacion,
                                       String estado,
                                       String descuento){

      Log.w("registraPedidosDET", "Sincronizando:::" +num_pedido );
      SyncPedidosWS sync_pedidos_det = MaservenApplication.getApplication().getRestAdapter().create(SyncPedidosWS.class);
      try{
          callInsertDetPedidos = sync_pedidos_det.setDatosDetPedidosWS(num_pedido,codigo_interno,codigo_alterno,cantidad,precio,fecha_creacion,fecha_actualizacion,estado,descuento);
      } catch (IllegalArgumentException e1) {
          Log.w("registraPedidosCab", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
          e1.printStackTrace();
      } catch (IllegalStateException e1) {
          e1.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }

      callInsertDetPedidos.enqueue(new Callback<SyncPedidosWS.DatosDetPedidos>() {
          @Override
          public void onResponse(Call<SyncPedidosWS.DatosDetPedidos> call, Response<SyncPedidosWS.DatosDetPedidos> response) {
              String err = "";
              try {
                  // err = response.errorBody().toString();
                  Log.w("registraPedidosCab", "Consultando respuesta" +err );
                  if (err.equalsIgnoreCase("")) {
                      if (response.body() != null) {
                          if (response.isSuccess()) {
                              SyncPedidosWS.DatosDetPedidos otp = response.body();

                              if (otp.getId_pedido().equals("-1")){
                                  Toast.makeText(MainActivity.this, "Problemas al sincronizar el Detalle de Pedidos", Toast.LENGTH_SHORT).show();

                              }else{
                                  Toast.makeText(MainActivity.this, "Detalle Pedido sincronizado Correctamente", Toast.LENGTH_SHORT).show();
                              }

                          } else {
                              Log.e("registraPedidosCab", "Error en el webservice, success false");

                          }
                      } else {
                          Log.e("registraPedidosCab", "Error de web service, no viene json");
                          //btn_sync.setEnabled(true);
                          Toast.makeText(MainActivity.this, "Problemas1 con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();

                      }
                  } else {
                      Log.e("registraPedidosCab", "Error en el webservice " + err);

                  }
              } catch (Exception e) {


                  Toast.makeText(MainActivity.this, "Problemas con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                  Log.w("registraPedidosCab", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());


              }
          }

          @Override
          public void onFailure(Call<SyncPedidosWS.DatosDetPedidos> call, Throwable t) {
              Log.w("registraPedidosDET", "onFailure - "+t.getMessage());
              Toast.makeText(MainActivity.this, "Probelmas con la Conexión, intente en unos momentos", Toast.LENGTH_SHORT).show();

          }
      });


  }

  public void GenerarDocumento(String num_pedido,String nombre_archivo,String email){

      Log.w("GenerarDocumento", "Sincronizando:::" +num_pedido );
      SyncPedidosWS generar_pedido = MaservenApplication.getApplication().getRestAdapter().create(SyncPedidosWS.class);
      try{
          callPedidoEnviado = generar_pedido.setDatosEnviadosWS(num_pedido,nombre_archivo,email);// .setDatosDetPedidosWS(num_pedido,codigo_interno,codigo_alterno,cantidad,precio,fecha_creacion,fecha_actualizacion,estado,descuento);
      } catch (IllegalArgumentException e1) {
          Log.w("GenerarDocumento", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
          e1.printStackTrace();
      } catch (IllegalStateException e1) {
          e1.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      callPedidoEnviado.enqueue(new Callback<SyncPedidosWS.DatosEnviados>() {
          @Override
          public void onResponse(Call<SyncPedidosWS.DatosEnviados> call, Response<SyncPedidosWS.DatosEnviados> response) {
              String err = "";
              try {
                  // err = response.errorBody().toString();
                  Log.w("GenerarDocumento", "Consultando respuesta" +err );
                  if (err.equalsIgnoreCase("")) {
                      if (response.body() != null) {
                          if (response.isSuccess()) {
                              SyncPedidosWS.DatosEnviados otp = response.body();

                              if (otp.getCodigo().equals("1")){
                                  Toast.makeText(MainActivity.this, "Pedido Generado Exitosamente", Toast.LENGTH_SHORT).show();

                              }else{
                                  Toast.makeText(MainActivity.this, "Problemas al Generar el Documento del Pedido en PDF", Toast.LENGTH_SHORT).show();
                              }

                          } else {
                              Log.e("GenerarDocumento", "Error en el webservice, success false");

                          }
                      } else {
                          Log.e("GenerarDocumento", "Error de web service, no viene json");
                          //btn_sync.setEnabled(true);
                          Toast.makeText(MainActivity.this, "Problemas1 con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();

                      }
                  } else {
                      Log.e("GenerarDocumento", "Error en el webservice " + err);

                  }
              } catch (Exception e) {


                  Toast.makeText(MainActivity.this, "Problemas con el servicio,  comuniquese con soporte MASERVEN...", Toast.LENGTH_SHORT).show();
                  Log.w("GenerarDocumento", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());


              }
          }

          @Override
          public void onFailure(Call<SyncPedidosWS.DatosEnviados> call, Throwable t) {
              Log.w("GenerarDocumento", "onFailure - "+t.getMessage());
              Toast.makeText(MainActivity.this, "Documento Generado, Revisar su Correo Electrónico.", Toast.LENGTH_SHORT).show();

          }
      });
  }

}
