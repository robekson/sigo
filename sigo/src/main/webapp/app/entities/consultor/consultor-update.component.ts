import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConsultor, Consultor } from 'app/shared/model/consultor.model';
import { ConsultorService } from './consultor.service';

@Component({
  selector: 'jhi-consultor-update',
  templateUrl: './consultor-update.component.html',
})
export class ConsultorUpdateComponent implements OnInit {
  isSaving = false;
  dataContratacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cnpj: [],
    dataContratacao: [null, [Validators.required]],
    email: [],
    telefone: [],
  });

  constructor(protected consultorService: ConsultorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultor }) => {
      this.updateForm(consultor);
    });
  }

  updateForm(consultor: IConsultor): void {
    this.editForm.patchValue({
      id: consultor.id,
      nome: consultor.nome,
      cnpj: consultor.cnpj,
      dataContratacao: consultor.dataContratacao,
      email: consultor.email,
      telefone: consultor.telefone,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consultor = this.createFromForm();
    if (consultor.id !== undefined) {
      this.subscribeToSaveResponse(this.consultorService.update(consultor));
    } else {
      this.subscribeToSaveResponse(this.consultorService.create(consultor));
    }
  }

  private createFromForm(): IConsultor {
    return {
      ...new Consultor(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      dataContratacao: this.editForm.get(['dataContratacao'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsultor>>): void {
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
