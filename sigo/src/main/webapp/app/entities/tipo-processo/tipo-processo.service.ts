import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoProcesso } from 'app/shared/model/tipo-processo.model';

type EntityResponseType = HttpResponse<ITipoProcesso>;
type EntityArrayResponseType = HttpResponse<ITipoProcesso[]>;

@Injectable({ providedIn: 'root' })
export class TipoProcessoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-processos';

  constructor(protected http: HttpClient) {}

  create(tipoProcesso: ITipoProcesso): Observable<EntityResponseType> {
    return this.http.post<ITipoProcesso>(this.resourceUrl, tipoProcesso, { observe: 'response' });
  }

  update(tipoProcesso: ITipoProcesso): Observable<EntityResponseType> {
    return this.http.put<ITipoProcesso>(this.resourceUrl, tipoProcesso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoProcesso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoProcesso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
