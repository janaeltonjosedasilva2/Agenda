package com.janaelton.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.janaelton.agenda.R;
import com.janaelton.agenda.dao.AlunoDAO;
import com.janaelton.agenda.model.Aluno;

import java.util.List;

import static com.janaelton.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";

    private final AlunoDAO dao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        setTitle(TITULO_APPBAR);

        configuraFabCriaAluno();
        criaListaAlunos();
        dao.salvar(new Aluno("Janaelton", "jana@gmail", "111111"));
        dao.salvar(new Aluno("Raiane", "rai@gmail", "222222"));
        dao.salvar(new Aluno("Gael", "Gael@gmail", "333333"));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater()
                .inflate(R.menu.activity_lista_aluno_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        CharSequence tituloDoMenu = item.getTitle();
        if (tituloDoMenu.equals("Remover")) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            removeAluno(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    private void configuraFabCriaAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListeDeAlunos();
    }

    private void atualizaListeDeAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void criaListaAlunos() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configuraAdpter(listaDeAlunos);
        configuraListenerDeClickPorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }

    private void removeAluno(Aluno aluno) {
        dao.remove(aluno);
        adapter.remove(aluno);
    }

    private void configuraListenerDeClickPorItem(ListView listaAluno) {
        listaAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) parent.getItemAtPosition(position);
                Log.i("aluno", "" + alunoEscolhido);

                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno alunoEscolhido) {
        Intent enviarParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        enviarParaFormularioActivity.putExtra(CHAVE_ALUNO, alunoEscolhido);
        startActivity(enviarParaFormularioActivity);
    }

    private void configuraAdpter(ListView listaAluno) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaAluno.setAdapter(adapter);
    }
}
