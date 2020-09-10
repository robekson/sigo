import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProcesso, Processo } from 'app/shared/model/processo.model';
import { ProcessoService } from './processo.service';

@Component({
  selector: 'jhi-processo-update',
  templateUrl: './processo-update.component.html',
})
export class ProcessoUpdateComponent implements OnInit {
  isSaving = false;
  processos: IProcesso[] = [];

  editForm = this.fb.group({
    id: [],
    idMateriaPrima: [],
    idProduto: [],
    nome: [],
    descricao: [],
    status: [],
    etapa: [],
    processoId: [],
  });

  constructor(protected processoService: ProcessoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processo }) => {
      this.updateForm(processo);

      this.processoService
        .query({ filter: 'processofilho-is-null' })
        .pipe(
          map((res: HttpResponse<IProcesso[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProcesso[]) => {
          if (!processo.processoId) {
            this.processos = resBody;
          } else {
            this.processoService
              .find(processo.processoId)
              .pipe(
                map((subRes: HttpResponse<IProcesso>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProcesso[]) => (this.processos = concatRes));
          }
        });
    });
  }

  updateForm(processo: IProcesso): void {
    this.editForm.patchValue({
      id: processo.id,
      idMateriaPrima: processo.idMateriaPrima,
      idProduto: processo.idProduto,
      nome: processo.nome,
      descricao: processo.descricao,
      status: processo.status,
      etapa: processo.etapa,
      processoId: processo.processoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const processo = this.createFromForm();
    if (processo.id !== undefined) {
      this.subscribeToSaveResponse(this.processoService.update(processo));
    } else {
      this.subscribeToSaveResponse(this.processoService.create(processo));
    }
  }

  private createFromForm(): IProcesso {
    return {
      ...new Processo(),
      id: this.editForm.get(['id'])!.value,
      idMateriaPrima: this.editForm.get(['idMateriaPrima'])!.value,
      idProduto: this.editForm.get(['idProduto'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      status: this.editForm.get(['status'])!.value,
      etapa: this.editForm.get(['etapa'])!.value,
      processoId: this.editForm.get(['processoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcesso>>): void {
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

  trackById(index: number, item: IProcesso): any {
    return item.id;
  }
}
