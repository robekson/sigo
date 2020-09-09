import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFuncionario, Funcionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from './funcionario.service';

@Component({
  selector: 'jhi-funcionario-update',
  templateUrl: './funcionario-update.component.html',
})
export class FuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  dataNascimentoDp: any;
  dataAdmissaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    dataNascimento: [],
    dataAdmissao: [null, [Validators.required]],
    cpf: [],
    rg: [],
    sexo: [],
    loginAcesso: [],
  });

  constructor(protected funcionarioService: FuncionarioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcionario }) => {
      this.updateForm(funcionario);
    });
  }

  updateForm(funcionario: IFuncionario): void {
    this.editForm.patchValue({
      id: funcionario.id,
      nome: funcionario.nome,
      dataNascimento: funcionario.dataNascimento,
      dataAdmissao: funcionario.dataAdmissao,
      cpf: funcionario.cpf,
      rg: funcionario.rg,
      sexo: funcionario.sexo,
      loginAcesso: funcionario.loginAcesso,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcionario = this.createFromForm();
    if (funcionario.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionarioService.update(funcionario));
    } else {
      this.subscribeToSaveResponse(this.funcionarioService.create(funcionario));
    }
  }

  private createFromForm(): IFuncionario {
    return {
      ...new Funcionario(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dataNascimento: this.editForm.get(['dataNascimento'])!.value,
      dataAdmissao: this.editForm.get(['dataAdmissao'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      rg: this.editForm.get(['rg'])!.value,
      sexo: this.editForm.get(['sexo'])!.value,
      loginAcesso: this.editForm.get(['loginAcesso'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
