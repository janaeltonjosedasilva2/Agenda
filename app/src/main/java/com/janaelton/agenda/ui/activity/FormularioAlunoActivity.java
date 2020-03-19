package com.janaelton.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.janaelton.agenda.R;
import com.janaelton.agenda.dao.AlunoDAO;
import com.janaelton.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Novo aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        setTitle(TITULO_APPBAR);

        inicializacaoDosCampos();

        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_btn_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno alunoCriado = criaAluno();

                salvar(alunoCriado, dao);
            }
        });
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_EditText_nomeAluno);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_EditText_telefoneAluno);
        campoEmail = findViewById(R.id.activity_formulario_aluno_EditText_emailAluno);
    }

    private void salvar(Aluno alunoCriado, AlunoDAO dao) {
        dao.salvar(alunoCriado);

        finish();
    }

    private Aluno criaAluno() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();

        return new Aluno(nome, email, telefone);
    }
}
