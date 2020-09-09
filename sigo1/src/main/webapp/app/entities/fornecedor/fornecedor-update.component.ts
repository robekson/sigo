import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';

@Component({
  selector: 'jhi-fornecedor-update',
  templateUrl: './fornecedor-update.component.html',
})
export class FornecedorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cnpj: [],
    email: [],
    telefone: [],
    razaoSocial: [],
  });

  constructor(protected fornecedorService: FornecedorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.updateForm(fornecedor);
    });
  }

  updateForm(fornecedor: IFornecedor): void {
    this.editForm.patchValue({
      id: fornecedor.id,
      nome: fornecedor.nome,
      cnpj: fornecedor.cnpj,
      email: fornecedor.email,
      telefone: fornecedor.telefone,
      razaoSocial: fornecedor.razaoSocial,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fornecedor = this.createFromForm();
    if (fornecedor.id !== undefined) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  private createFromForm(): IFornecedor {
    return {
      ...new Fornecedor(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      razaoSocial: this.editForm.get(['razaoSocial'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>): void {
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
