import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { NormasDetailComponent } from 'app/entities/normas/normas-detail.component';
import { Normas } from 'app/shared/model/normas.model';

describe('Component Tests', () => {
  describe('Normas Management Detail Component', () => {
    let comp: NormasDetailComponent;
    let fixture: ComponentFixture<NormasDetailComponent>;
    const route = ({ data: of({ normas: new Normas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [NormasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NormasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NormasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load normas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.normas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
