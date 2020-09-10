import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFornece, Fornece } from 'app/shared/model/fornece.model';
import { ForneceService } from './fornece.service';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';

@Component({
  selector: 'jhi-fornece-update',
  templateUrl: './fornece-update.component.html',
})
export class ForneceUpdateComponent implements OnInit {
  isSaving = false;
  fornecedors: IFornecedor[] = [];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.required]],
    data: [null, [Validators.required]],
    tamanho: [],
    valor: [],
    fornecedorId: [],
  });

  constructor(
    protected forneceService: ForneceService,
    protected fornecedorService: FornecedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornece }) => {
      this.updateForm(fornece);

      this.fornecedorService.query().subscribe((res: HttpResponse<IFornecedor[]>) => (this.fornecedors = res.body || []));
    });
  }

  updateForm(fornece: IFornece): void {
    this.editForm.patchValue({
      id: fornece.id,
      quantidade: fornece.quantidade,
      data: fornece.data,
      tamanho: fornece.tamanho,
      valor: fornece.valor,
      fornecedorId: fornece.fornecedorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fornece = this.createFromForm();
    if (fornece.id !== undefined) {
      this.subscribeToSaveResponse(this.forneceService.update(fornece));
    } else {
      this.subscribeToSaveResponse(this.forneceService.create(fornece));
    }
  }

  private createFromForm(): IFornece {
    return {
      ...new Fornece(),
      id: this.editForm.get(['id'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      data: this.editForm.get(['data'])!.value,
      tamanho: this.editForm.get(['tamanho'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      fornecedorId: this.editForm.get(['fornecedorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornece>>): void {
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

  trackById(index: number, item: IFornecedor): any {
    return item.id;
  }
}
