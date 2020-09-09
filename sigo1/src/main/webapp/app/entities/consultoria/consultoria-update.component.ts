import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConsultoria, Consultoria } from 'app/shared/model/consultoria.model';
import { ConsultoriaService } from './consultoria.service';

@Component({
  selector: 'jhi-consultoria-update',
  templateUrl: './consultoria-update.component.html',
})
export class ConsultoriaUpdateComponent implements OnInit {
  isSaving = false;
  dataContratacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cnpj: [],
    dataContratacao: [null, [Validators.required]],
    email: [],
    telefone: [],
    tipoServicoPrestado: [],
  });

  constructor(protected consultoriaService: ConsultoriaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultoria }) => {
      this.updateForm(consultoria);
    });
  }

  updateForm(consultoria: IConsultoria): void {
    this.editForm.patchValue({
      id: consultoria.id,
      nome: consultoria.nome,
      cnpj: consultoria.cnpj,
      dataContratacao: consultoria.dataContratacao,
      email: consultoria.email,
      telefone: consultoria.telefone,
      tipoServicoPrestado: consultoria.tipoServicoPrestado,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consultoria = this.createFromForm();
    if (consultoria.id !== undefined) {
      this.subscribeToSaveResponse(this.consultoriaService.update(consultoria));
    } else {
      this.subscribeToSaveResponse(this.consultoriaService.create(consultoria));
    }
  }

  private createFromForm(): IConsultoria {
    return {
      ...new Consultoria(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      dataContratacao: this.editForm.get(['dataContratacao'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      tipoServicoPrestado: this.editForm.get(['tipoServicoPrestado'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsultoria>>): void {
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
